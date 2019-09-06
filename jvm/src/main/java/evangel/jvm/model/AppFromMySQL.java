package evangel.jvm.model;

import java.util.Date;

/**
 * 关系型数据库中用户已安装app
 */
public class AppFromMySQL {
	private int id;
	private Long userId;
	private String packageName;
	private int versionCode;
	private Date installTime;
	private String appName;
	private String iconUrl;
	private String downloadUrl;
	private String remark;
	private Long size;
	private String developer;

	public AppFromMySQL(int id, Long userId, String packageName,
			int versionCode, Date installTime, String appName) {
		this.id = id;
		this.userId = userId;
		this.packageName = packageName;
		this.versionCode = versionCode;
		this.installTime = installTime;
		this.appName = appName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public Date getInstallTime() {
		return installTime;
	}

	public void setInstallTime(Date installTime) {
		this.installTime = installTime;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}
}
