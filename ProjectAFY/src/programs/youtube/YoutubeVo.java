package programs.youtube;
import java.sql.Timestamp;

public class YoutubeVo{
	private String yt_seq; //시퀀스
	private String yt_title; //제목
	private String yt_content; //내용
	private String yt_id; //유튜브 영상id
	private String yt_cnt; //영상조회수
	private Timestamp yt_date; //게시일
	private String yt_img; //썸네일

	public String getYt_seq() {
		return yt_seq == null ? "" : yt_seq;
	}
	public String getYt_title() {
		return yt_title == null ? "" : yt_title;
	}
	public String getYt_content() {
		return yt_content == null ? "" : yt_content;
	}
	public String getYt_id() {
		return yt_id == null ? "" : yt_id;
	}
	public String getYt_cnt() {
		return yt_cnt == null ? "" : yt_cnt;
	}
	public Timestamp getYt_date() {
		return yt_date;
	}
	public String getYt_img() {
		return yt_img == null ? "" : yt_img;
	}

	public void setYt_seq(String yt_seq) {
		this.yt_seq = yt_seq;
	}
	public void setYt_title(String yt_title) {
		this.yt_title = yt_title;
	}
	public void setYt_content(String yt_content) {
		this.yt_content = yt_content;
	}
	public void setYt_id(String yt_id) {
		this.yt_id = yt_id;
	}
	public void setYt_cnt(String yt_cnt) {
		this.yt_cnt = yt_cnt;
	}
	public void setYt_date(Timestamp yt_date) {
		this.yt_date = yt_date;
	}
	public void setYt_img(String yt_img) {
		this.yt_img = yt_img;
	}
}