package prometheus.messages.util;


import prometheus.messages.entity.Pending_messages;
import prometheus.messages.threads.FetchThread;

public class GetSendMessage {
	/**
	 * 获取内存中的发送数据
	 * @param count 抓取数量
	 * @return Pending_messages[]
	 * */
	public static Pending_messages[] getPending_messages(int count){
		int batchNum = 0;
		int size = FetchThread.getVlist().size();
		if (count > size) {
			batchNum = size;
		} else {
			batchNum = count;
		}
		Pending_messages[] result = new Pending_messages[batchNum];
		Pending_messages gwi = null;
		for (int i = 0; i < batchNum; i++) {
			gwi = get();
			if(gwi != null)
			{
				result[i] = gwi;
			}
		}

		return result;
	}

	public synchronized static Pending_messages get() {
		Pending_messages gwi;
		if (FetchThread.getVlist().isEmpty()) {
			return null;
		} else {
			gwi = FetchThread.getVlist().remove(FetchThread.getVlist().size() - 1);
		}
		return gwi;
	}
		
}
