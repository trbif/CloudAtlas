package cloud.bean;

public class FeedbackInfo extends BaseBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 451870137321525841L;
	
	
	private String infoSource;
	private String href;
	private String title;
	private String publishDate;
	private String phoneModule;
	
	private FeedbackInfo() {}
	
	public FeedbackInfo(String infoSource) {
		this.infoSource = infoSource;
	}

	public String getInfoSource() {
		return infoSource;
	}

	public void setInfoSource(String infoSource) {
		this.infoSource = infoSource;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getPhoneModule() {
		return phoneModule;
	}

	public void setPhoneModule(String phoneModule) {
		this.phoneModule = phoneModule;
	}
	
	

}
