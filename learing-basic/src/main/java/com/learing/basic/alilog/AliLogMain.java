package com.learing.basic.alilog;

import com.aliyun.openservices.loghub.client.ClientWorker;
import com.aliyun.openservices.loghub.client.config.LogHubConfig;
import com.aliyun.openservices.loghub.client.exceptions.LogHubClientWorkerException;

/**
 * @Author lvjun@csdn.net
 * @Date 2021/1/5 5:32 下午
 * @Modified By:
 */
public class AliLogMain {

    /**
     * todo ali日志配置项
     */
    // 日志服务域名，请您根据实际情况填写。
    private static String sEndpoint = "cn-hangzhou.log.aliyuncs.com";
    // 日志服务项目名称，请您根据实际情况填写。
    private static String sProject = "ali-cn-hangzhou-sls-admin";
    // 日志库名称，请您根据实际情况填写。
    private static String sLogstore = "sls_operation_log";
    // 消费组名称，请您根据实际情况填写。
    private static String sConsumerGroup = "consumerGroupX";
    // 消费数据的用户AK信息，请您根据实际情况填写。
    private static String sAccessKeyId = "";
    private static String sAccessKey = "";

    /**
     * todo 任务启动
     * @param args
     * @throws LogHubClientWorkerException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws LogHubClientWorkerException, InterruptedException {
        // consumer_1是消费者名称，同一个消费组下面的消费者名称必须不同，不同的消费者名称在多台机器上启动多个进程，来均衡消费一个Logstore，此时消费者名称可以使用机器ip来区分。maxFetchLogGroupSize是每次从服务端获取的LogGroup最大数目，使用默认值即可，如有调整请注意取值范围(0,1000]。
        LogHubConfig config = new LogHubConfig(sConsumerGroup, "consumer_1", sEndpoint, sProject, sLogstore, sAccessKeyId, sAccessKey, LogHubConfig.ConsumePosition.BEGIN_CURSOR);
        //todo 启动任务
        ClientWorker worker = new ClientWorker(new SampleLogHubProcessorFactory(), config);
        Thread thread = new Thread(worker);
        //Thread运行之后，ClientWorker会自动运行，ClientWorker扩展了Runnable接口。
        thread.start();
        Thread.sleep(60 * 60 * 1000);
        //调用Worker的Shutdown函数，退出消费实例，关联的线程也会自动停止。
        worker.shutdown();
        //ClientWorker运行过程中会生成多个异步的Task，Shutdown完成后请等待还在执行的Task安全退出，建议sleep配置为30s。
        Thread.sleep(30 * 1000);
    }





}
