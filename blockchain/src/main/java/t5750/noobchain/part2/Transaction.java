package t5750.noobchain.part2;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {
	public String transactionId; // Contains a hash of transaction*
	public PublicKey sender; // Senders address/public key.
	public PublicKey reciepient; // Recipients address/public key.
	public float value; // Contains the amount we wish to send to the recipient.
	public byte[] signature; // This is to prevent anybody else from spending
								// funds in our wallet.
	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
	// A rough count of how many transactions have been generated
	private static int sequence = 0;

	// Constructor:
	public Transaction(PublicKey from, PublicKey to, float value,
			ArrayList<TransactionInput> inputs) {
		this.sender = from;
		this.reciepient = to;
		this.value = value;
		this.inputs = inputs;
	}

	public boolean processTransaction() {
		if (verifySignature() == false) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}
		// Gathers transaction inputs (Making sure they are unspent):
		for (TransactionInput i : inputs) {
			i.UTXO = NoobChain.UTXOs.get(i.transactionOutputId);
		}
		// Checks if transaction is valid:
		if (getInputsValue() < NoobChain.minimumTransaction) {
			System.out.println(
					"Transaction Inputs too small: " + getInputsValue());
			System.out.println("Please enter the amount greater than "
					+ NoobChain.minimumTransaction);
			return false;
		}
		// Generate transaction outputs:
		// get value of inputs then the left over change:
		float leftOver = getInputsValue() - value;
		transactionId = calulateHash();
		// send value to recipient
		outputs.add(
				new TransactionOutput(this.reciepient, value, transactionId));
		// send the left over 'change' back to sender
		outputs.add(
				new TransactionOutput(this.sender, leftOver, transactionId));
		// Add outputs to Unspent list
		for (TransactionOutput o : outputs) {
			NoobChain.UTXOs.put(o.id, o);
		}
		// Remove transaction inputs from UTXO lists as spent:
		for (TransactionInput i : inputs) {
			if (i.UTXO == null)
				continue; // if Transaction can't be found skip it
			NoobChain.UTXOs.remove(i.UTXO.id);
		}
		return true;
	}

	public float getInputsValue() {
		float total = 0;
		for (TransactionInput i : inputs) {
			// if Transaction can't be found skip it, This behavior may not be
			// optimal.
			if (i.UTXO == null)
				continue;
			total += i.UTXO.value;
		}
		return total;
	}

	public void generateSignature(PrivateKey privateKey) {
		String data = StringUtil.getStringFromKey(sender)
				+ StringUtil.getStringFromKey(reciepient)
				+ Float.toString(value);
		signature = StringUtil.applyECDSASig(privateKey, data);
	}

	public boolean verifySignature() {
		String data = StringUtil.getStringFromKey(sender)
				+ StringUtil.getStringFromKey(reciepient)
				+ Float.toString(value);
		return StringUtil.verifyECDSASig(sender, data, signature);
	}

	public float getOutputsValue() {
		float total = 0;
		for (TransactionOutput o : outputs) {
			total += o.value;
		}
		return total;
	}

	private String calulateHash() {
		// increase the sequence to avoid 2 identical transactions having the
		// same hash
		sequence++;
		return StringUtil.applySha256(StringUtil.getStringFromKey(sender)
				+ StringUtil.getStringFromKey(reciepient)
				+ Float.toString(value) + sequence);
	}
}
