package webApp.http;

public class SessionCreateTime {
	String Id;
	long createTime;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
