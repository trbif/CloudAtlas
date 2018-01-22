package cloud.service;

import java.util.List;

import cloud.bean.sql.FeedbackBean;

public interface FeedbackService {
	Long saveBatch(List<FeedbackBean> list);
}
