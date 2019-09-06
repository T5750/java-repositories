package evangel.jvm.model;

import java.util.List;

/**
 * 需要保存到MongoDB中的用户已安装app信息，这样保存的好处就是MongoDB中installed_apps这张表的user_id能设置唯一键约束，
 * 查询性能相比RDBMS中数据平铺要高不少
 */
public class UserAppMongo {
	private String id;
	private Long userId;
	private List<AppMongo> appMongoList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<AppMongo> getAppMongoList() {
		return appMongoList;
	}

	public void setAppMongoList(List<AppMongo> appMongoList) {
		this.appMongoList = appMongoList;
	}
}
