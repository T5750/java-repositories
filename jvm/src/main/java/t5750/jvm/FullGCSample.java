package t5750.jvm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import t5750.jvm.model.AppFromMySQL;
import t5750.jvm.model.AppMongo;
import t5750.jvm.model.UserAppMongo;

/**
 * OMG！又一个频繁FullGC案例<br/>
 * https://www.jianshu.com/p/c90dc5495a4f
 */
public class FullGCSample {
	// -Xmx400m -Xms400m -Xmn150m -verbose:gc -XX:+PrintGCDetails
	// -XX:+HeapDumpBeforeFullGC
	public static void main(String[] args) throws Exception {
		// final int threshold = 500;
		for (int pageNo = 0; pageNo < 10000; pageNo++) {
			List<Long> userList = getUserIdByPage(pageNo);
			List<UserAppMongo> userAppMongoList = new ArrayList<>(
					userList.size());
			for (Long userId : userList) {
				List<AppFromMySQL> appFromMySQLList = getUserInstalledAppList(userId);
				UserAppMongo userAppMongo = new UserAppMongo();
				userAppMongo.setId(System.nanoTime() + "");// 测试代码任意模拟一个伪唯一ID
				userAppMongo.setUserId(userId);
				userAppMongo
						.setAppMongoList(appFromMySQL2AppMongo(appFromMySQLList));
				userAppMongoList.add(userAppMongo);
				// 核心优化代码
				// if (userAppMongoList.size() >= threshold) {
				// save2MongoDB(userAppMongoList);
				// userAppMongoList.clear();
				// }
			}
			// save List<UserAppMongo> to mongodb
			save2MongoDB(userAppMongoList);
		}
	}

	private static void save2MongoDB(List<UserAppMongo> userAppMongoList)
			throws Exception {
		// 模拟保存一次数据到mongodb中要5ms
		Thread.sleep(5);
	}

	private static List<AppMongo> appFromMySQL2AppMongo(List<AppFromMySQL> list) {
		List<AppMongo> appMongoList = new ArrayList<>();
		for (AppFromMySQL app : list) {
			AppMongo appMongo = new AppMongo();
			appMongoList.add(appMongo);
		}
		return appMongoList;
	}

	private static List<AppFromMySQL> getUserInstalledAppList(Long useId) {
		List<AppFromMySQL> appFromMySQLList = new ArrayList<>();
		// 假设用户手机上安装的app数量在50~200之间
		int size = 50 + new Random().nextInt(150);
		for (int i = 0; i < size; i++) {
			AppFromMySQL appFromMySQL = new AppFromMySQL(i, (long) i,
					"com.android" + i, i, new Date(), "appName" + i);
			appFromMySQL.setIconUrl(String.valueOf(i));
			appFromMySQL.setDownloadUrl(String.valueOf(i));
			appFromMySQL.setRemark(String.valueOf(i));
			appFromMySQL.setSize((long) i);
			appFromMySQL.setDeveloper(String.valueOf(i));
			appFromMySQLList.add(appFromMySQL);
		}
		return appFromMySQLList;
	}

	private static List<Long> getUserIdByPage(int pageNo) {
		List<Long> userList = new ArrayList<>();
		// 取数据时每一页1000个用户
		for (int i = 0; i < 2000; i++) {
			userList.add((long) i);
		}
		return userList;
	}
}