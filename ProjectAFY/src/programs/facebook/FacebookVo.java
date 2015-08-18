package programs.facebook;

import java.sql.Timestamp;

public class FacebookVo{
	private String fb_content; //내용
	private String fb_img; //이미지URL
	private String fb_url; //내용 URL
	private Timestamp reg_dt; //작성일

	public String getFb_content() {
		return fb_content == null ? "" : fb_content;
	}
	public String getFb_img() {
		return fb_img == null ? "" : fb_img;
	}
	public String getFb_url() {
		return fb_url == null ? "" : fb_url;
	}
	public Timestamp getReg_dt() {
		return reg_dt;
	}
	public void setFb_content(String fb_content) {
		this.fb_content = fb_content;
	}
	public void setFb_img(String fb_img) {
		this.fb_img = fb_img;
	}
	public void setFb_url(String fb_url) {
		this.fb_url = fb_url;
	}
	public void setReg_dt(Timestamp reg_dt) {
		this.reg_dt = reg_dt;
	}
}