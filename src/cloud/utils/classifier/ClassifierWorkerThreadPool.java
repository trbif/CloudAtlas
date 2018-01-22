package cloud.utils.classifier;

我:
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.nubia.bean.sql.ClassifyMissionStatusBean;
import cn.nubia.service.ClassifyMissionStatusService;
import cn.nubia.utils.TrainEnum;
import w2v.classify.W2VClassifier;
我:
/**
 * @author zhangqi
 * @date 2017年12月4日 下午7:36:09
 * @version V1.0
 * @说明:
 */
public class ClassifierWorkerThreadPool extends Thread {

	private final static Logger LOGGER = LoggerFactory.getLogger(ClassifierWorkerThreadPool.class);

	private String wordGapList[];

	private String vectorDimList[];

	private String lineCountList[];

	private String windowSizeList[];

	private Map<String, Float> score = new HashMap<>();
我:
	// private Map<String,Float> scoreper = new HashMap<>();
	// private Map<String,List<Float>> scoreperarray = new HashMap<>();
	// private Map<Integer,Float> w_score = new HashMap<>();
	// private Map<Integer,Float> v_score = new HashMap<>();
	// private Map<Integer,Float> l_score = new HashMap<>();
	// private Map<Integer,List<Float>> w_score_list = new HashMap<>();
	// private Map<Integer,List<Float>> v_score_list = new HashMap<>();
	// private Map<Integer,List<Float>> l_score_list = new HashMap<>();
	private int progresscount;
	private int totalcount;

	private int categoryCounts;

	private String corpusPath;
	private String testCorpusPath;
	private int showCount;
	private long userId;
	private ClassifyMissionStatusService classifyMissionStatusService;
	private long missionId;
	private float topScore = 0.0f;
	private String topScoreKey = "";

	private ClassifierWorkerThreadPool() {
	}
我:
	private ClassifierWorkerThreadPool(long userId,String corpusPath, String testCorpusPath, int showCount,ClassifyMissionStatusService classifyMissionStatusService) {
		this.userId = userId;
		this.corpusPath = corpusPath;
		this.testCorpusPath = testCorpusPath;
		this.showCount = showCount;
		this.classifyMissionStatusService = classifyMissionStatusService;
		ClassifyMissionStatusBean bean = new ClassifyMissionStatusBean();
		bean.setOwnerId(userId);
		bean.setStatus(0);
		missionId = classifyMissionStatusService.save(bean);
	}

	public static ClassifierWorkerThreadPool newInstance(long userId,String corpusPath, String testCorpusPath,ClassifyMissionStatusService classifyMissionStatusService) {
		return new ClassifierWorkerThreadPool(userId,corpusPath, testCorpusPath, 1,classifyMissionStatusService);
	}

	public static ClassifierWorkerThreadPool newInstance(long userId,String corpusPath, String testCorpusPath, int showCount,ClassifyMissionStatusService classifyMissionStatusService) {
		return new ClassifierWorkerThreadPool(userId,corpusPath, testCorpusPath, showCount,classifyMissionStatusService);
	}
	
	public long getMissionId(){
		return missionId;
	}
我:
	// private ClassifierWorkerThreadPool(String corpusPath,String
	// testCorpusPath,String[] wordGapList,String[] vectorDimList,String[]
	// lineCountList,int showCount){
	// this.corpusPath = corpusPath;
	// this.testCorpusPath = testCorpusPath;
	// this.wordGapList = wordGapList;
	// this.vectorDimList = vectorDimList;
	// this.lineCountList = lineCountList;
	// this.showCount = showCount;
	// }
	//
	// public static ClassifierWorkerThreadPool newInstance(String
	// corpusPath,String testCorpusPath,String[] wordGapList,String[]
	// vectorDimList,String[] lineCountList){
	// return
	// newInstance(corpusPath,testCorpusPath,wordGapList,vectorDimList,lineCountList,5);
	// }
	//
	// public static ClassifierWorkerThreadPool newInstance(String
	// corpusPath,String testCorpusPath,String[] wordGapList,String[]
	// vectorDimList,String[] lineCountList,int showCount){
	// return new
	// ClassifierWorkerThreadPool(corpusPath,testCorpusPath,wordGapList,vectorDimList,lineCountList,showCount);
	// }
我:
	public List<Map.Entry<String, Float>> getResult() {
		List<Map.Entry<String, Float>> list = new ArrayList<Map.Entry<String, Float>>(score.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Float>>() {
			// 降序排序
			public int compare(Entry<String, Float> o1, Entry<String, Float> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		return list;
	}
	// private Map<Integer, Float> sortMapByKey(Map<Integer, Float> map) {
	// if (map == null || map.isEmpty()) {
	// return null;
	// }
	// Map<Integer, Float> sortMap = new TreeMap<Integer, Float>(new
	// MapKeyComparator());
	//
	// sortMap.putAll(map);
	//
	// return sortMap;
	// }
我:
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		init();
	}
我:
	// private Map<Integer, List<Float>> sortMapByKeyList(Map<Integer,
	// List<Float>> map) {
	// if (map == null || map.isEmpty()) {
	// return null;
	// }
	// Map<Integer, List<Float>> sortMap = new TreeMap<Integer, List<Float>>(new
	// MapKeyComparator());
	//
	// sortMap.putAll(map);
	//
	// return sortMap;
	// }
	// class MapKeyComparator implements Comparator<Integer>{
	//
	// @Override
	// public int compare(Integer int1, Integer int2) {
	//
	// return int1.compareTo(int2);
	// }
	// }
	//
	// public Map<Integer,Float> getWResult(){
	// return sortMapByKey(w_score);
	// }
	//
	// public Map<Integer,Float> getVResult(){
	// return sortMapByKey(v_score);
	// }
	//
	// public Map<Integer,Float> getLResult(){
	// return sortMapByKey(l_score);
	// }
我:
	//
	// public Map<Integer,List<Float>> getWResultList(){
	// return sortMapByKeyList(w_score_list);
	// }
	//
	// public Map<Integer,List<Float>> getVResultList(){
	// return sortMapByKeyList(v_score_list);
	// }
	//
	// public Map<Integer,List<Float>> getLResultList(){
	// return sortMapByKeyList(l_score_list);
	// }

	public float getProgress() {
		return (totalcount == 0) ? (0) : ((float) progresscount / (float) totalcount);
	}

	public String getFirst() {
		return getResult().get(0).toString();
	}
我:
	private void init() {
		File file = new File(corpusPath);
		categoryCounts = file.list().length;
		categoryCounts = (categoryCounts / 10) * 10;
		String type = TrainEnum.getValue(categoryCounts);
		if (type == null) {
			LOGGER.error("类别个数有误：{}", categoryCounts);
			return;
		}
		String[] param = type.split("#");
		if (param.length != 3) {
			LOGGER.error("参数字串有误，字串类别个数：{}", param.length);
			return;
		}
		wordGapList = "1,2,3,4".split(",");
		vectorDimList = param[1].split(",");
		lineCountList = "1,2,3".split(",");
		windowSizeList = "5,6,7,8,9,10,11,11,13,14".split(",");
		totalcount = wordGapList.length * vectorDimList.length * lineCountList.length*windowSizeList.length;
		ClassifyMissionStatusBean bean = new ClassifyMissionStatusBean();
		bean.setId(missionId);
		bean.setTotalCount(totalcount);
		bean.setStatus(1);
我:
		classifyMissionStatusService.saveOrUpdate(bean);
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(7);
		long timestamp = Calendar.getInstance().getTimeInMillis();
		for (int i = 0; i < wordGapList.length; i++) {
			for (int j = 0; j < vectorDimList.length; j++) {
				for (int k = 0; k < lineCountList.length; k++) {
					for (int m = 0; m < windowSizeList.length; m++) {
						int id = j * 1000000 + i * 10000 + k*100 + m;
						fixedThreadPool.execute(new ClassifierWorker(id, Integer.valueOf(wordGapList[i]),
								Integer.valueOf(vectorDimList[j]), Integer.valueOf(lineCountList[k]), Integer.valueOf(windowSizeList[m]), showCount, timestamp,
								corpusPath, testCorpusPath));
					}
				}
			}
		}
		if (!fixedThreadPool.isShutdown()) {
			fixedThreadPool.shutdown();
		}
我:
//		try {
//			boolean loop = true;
//			do { // 等待所有任务完成
//				loop = !fixedThreadPool.awaitTermination(2, TimeUnit.SECONDS); // 阻塞，直到线程池里所有任务结束
//			} while (loop);
//			System.out.println(getProgress() + "");
//			// System.out.println(getWResult());
//			// System.out.println(getVResult());
//			// System.out.println(getLResult());
//			// System.out.println(getWResultList());
//			// System.out.println(getVResultList());
//			// System.out.println(getLResultList());
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
我:
	public class ClassifierWorker implements Runnable {
		int id;
		int wordGap;
		int vectorDim;
		int lineCount;
		int windowSize;
		int testParam;
		long timestamp;
		// int w_count;
		// int v_count;
		// int l_count;
		// int w_count_a;
		// int v_count_a;
		// int l_count_a;

		public ClassifierWorker(int id, int wordGap, int vectorDim, int lineCount,int windowSize, int testParam, long timestamp,
				String corpusPath, String testCorpusPath) {
			this.id = id;
			this.wordGap = wordGap;
			this.vectorDim = vectorDim;
			this.lineCount = lineCount;
			this.windowSize = windowSize;
			this.testParam = testParam;
			this.timestamp = timestamp;
		}
我:
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String w2vKey = userId + "_" + id + "_" + missionId;
			W2VClassifier wc = new W2VClassifier.
					Builder(w2vKey)
					.setCorpusFolderPath(corpusPath)
					.setTestCorpusFolderPath(testCorpusPath)
					.setVectorDim(vectorDim)
					.setLineCount(lineCount)
					.setWordGap(wordGap)
					.setWindowSize(windowSize)
					.setTestParam(testParam)
					.build();
我:
			try {
				wc.genTrainUseFiles();
				wc.classifiedJudgementFile();
//				Float finalScore = wc.getAvgScore();
				Float finalScore = wc.getHitRate();
				// List<Float> finalScoreArray = wc.getAvgScoreArray();
				// synchronized(w_score){
				// Integer key = wordGap;
				// float score = finalScore;
				// if(w_score.containsKey(key)){
				// w_count++;
				// score += w_score.get(key);
				// score /= (float)(w_count+1);
				// }
				// w_score.put(key, score);
				// }
				// finalScoreArray = wc.getAvgScoreArray();
				// synchronized(w_score_list){
				// Integer key = wordGap;
				// List<Float> scoreArray = finalScoreArray;
				// if(w_score_list.containsKey(key)){
				// w_count_a++;
				// for(int i=0;i<scoreArray.size();i++){
				// float num = scoreArray.get(i);
				// scoreArray.remove(i);
				// scoreArray.add(i, (num
				// +w_score_list.get(key).get(i))/(float)(w_count_a+1));
				// }
				// }
我:
				// w_score_list.put(key, scoreArray);
				// }
				// synchronized(v_score){
				// Integer key = vectorDim;
				// float score = finalScore;
				// if(v_score.containsKey(key)){
				// v_count++;
				// score += v_score.get(key);
				// score /= (float)(v_count+1);
				// }
				// v_score.put(key, score);
				// }
				// finalScoreArray = wc.getAvgScoreArray();
				// synchronized(v_score_list){
				// Integer key = vectorDim;
				// List<Float> scoreArray = finalScoreArray;
				// if(v_score_list.containsKey(key)){
				// v_count_a++;
				// for(int i=0;i<scoreArray.size();i++){
				// float num = scoreArray.get(i);
				// scoreArray.remove(i);
				// scoreArray.add(i, (num
				// +v_score_list.get(key).get(i))/(float)(v_count_a+1));
				// }
				// }
我:
				// v_score_list.put(key, scoreArray);
				// }
				// synchronized(l_score){
				// Integer key = lineCount;
				// float score = finalScore;
				// if(l_score.containsKey(key)){
				// l_count++;
				// score += l_score.get(key);
				// score /= (float)(l_count+1);
				// }
				// l_score.put(key, score);
				// }
				// synchronized(l_score_list){
				// Integer key = lineCount;
				// List<Float> scoreArray = finalScoreArray;
				// if(l_score_list.containsKey(key)){
				// l_count_a++;
				// for(int i=0;i<scoreArray.size();i++){
				// float num = scoreArray.get(i);
				// scoreArray.remove(i);
				// scoreArray.add(i, (num
				// +l_score_list.get(key).get(i))/(float)(l_count_a+1));
				// }
				// }
				// l_score_list.put(key, scoreArray);
				// }
我:
				synchronized (score) {
					score.put(w2vKey, finalScore);
					progresscount++;
					ClassifyMissionStatusBean bean = new ClassifyMissionStatusBean();
					bean.setId(missionId);
					bean.setOwnerId(userId);
					bean.setProgressCount(progresscount);
					bean.setTotalCount(totalcount);
					if(topScore<finalScore.floatValue()){
						topScore = finalScore.floatValue();
						topScoreKey = w2vKey;
					}
					bean.setOptimalSolution(topScoreKey+":"+topScore);
					if(progresscount!=totalcount)
						bean.setStatus(1);
					else
						bean.setStatus(2);
					classifyMissionStatusService.saveOrUpdate(bean);
				}
我:
				// synchronized(scoreper){
				// scoreper.put(w2vKey, wc.getScoreCountPer(0.8f));
				// }
				// synchronized(scoreperarray){
				// scoreperarray.put(w2vKey, wc.getAvgScoreArray());
				// }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}