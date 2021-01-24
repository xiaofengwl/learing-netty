package com.learing.basic.alilog;

import com.aliyun.openservices.log.common.FastLog;
import com.aliyun.openservices.log.common.FastLogContent;
import com.aliyun.openservices.log.common.FastLogGroup;
import com.aliyun.openservices.log.common.FastLogTag;
import com.aliyun.openservices.log.common.LogGroupData;
import com.aliyun.openservices.loghub.client.ILogHubCheckPointTracker;
import com.aliyun.openservices.loghub.client.exceptions.LogHubCheckPointException;
import com.aliyun.openservices.loghub.client.interfaces.ILogHubProcessor;
import com.aliyun.openservices.loghub.client.interfaces.ILogHubProcessorFactory;

import java.util.List;
/**
 * @Author lvjun@csdn.net
 * @Date 2021/1/5 5:32 下午
 * @Modified By:
 */
public class SampleLogHubProcessor implements ILogHubProcessor {
    private int shardId;
    // 记录上次持久化Checkpoint的时间。
    private long mLastCheckTime = 0;

    public void initialize(int shardId) {
        this.shardId = shardId;
    }

    /**
     * todo 消费数据的主逻辑，此处所有异常都需要获取，不能抛出去。
     * @param logGroups
     * @param checkPointTracker
     * @return
     */
    @Override
    public String process(List<LogGroupData> logGroups,
                          ILogHubCheckPointTracker checkPointTracker) {
        // 将获取到的数据打印出来。
        for (LogGroupData logGroup : logGroups) {
            FastLogGroup flg = logGroup.GetFastLogGroup();

            System.out.println(String.format("\tcategory\t:\t%s\n\tsource\t:\t%s\n\ttopic\t:\t%s\n\tmachineUUID\t:\t%s",
                    flg.getCategory(),
                    flg.getSource(),
                    flg.getTopic(),
                    flg.getMachineUUID()));
            System.out.println("Tags");


            for (int tagIdx = 0; tagIdx < flg.getLogTagsCount(); ++tagIdx) {
                FastLogTag logtag = flg.getLogTags(tagIdx);
                System.out.println(String.format("\t%s\t:\t%s",
                        logtag.getKey(),
                        logtag.getValue()));
            }

            for (int lIdx = 0; lIdx < flg.getLogsCount(); ++lIdx) {
                FastLog log = flg.getLogs(lIdx);
                System.out.println("--------\nLog: " + lIdx + ", time: " + log.getTime() + ", GetContentCount: " + log.getContentsCount());

                for (int cIdx = 0; cIdx < log.getContentsCount(); ++cIdx) {
                    FastLogContent content = log.getContents(cIdx);
                    System.out.println(content.getKey() + "\t:\t" +
                                    content.getValue());
                }
            }
        }
        long curTime = System.currentTimeMillis();
        // 每隔30秒，写一次Checkpoint到服务端，如果30秒内发生Worker crash，新启动的Worker会从上一个Checkpoint获取消费数据，可能存在少量的重复数据。
        if (curTime - mLastCheckTime > 30 * 1000) {
            try {
                //参数为true表示立即将Checkpoint更新到服务端，为false会将Checkpoint缓存在本地，默认隔60s会将Checkpoint更新到服务端。
                checkPointTracker.saveCheckPoint(true);
            } catch (LogHubCheckPointException e) {
                e.printStackTrace();
            }
            mLastCheckTime = curTime;
        }
        return null;
    }

    // 当Worker退出时，会调用该函数，您可以在此处执行清理工作。
    public void shutdown(ILogHubCheckPointTracker checkPointTracker) {
        //将Checkpoint保存到服务端。
        try {
            checkPointTracker.saveCheckPoint(true);
        } catch (LogHubCheckPointException e) {
            e.printStackTrace();
        }
    }
}

/**
 *
 */
class SampleLogHubProcessorFactory implements ILogHubProcessorFactory {
    public ILogHubProcessor generatorProcessor() {
        // 生成一个消费实例。
        return new SampleLogHubProcessor();
    }
}