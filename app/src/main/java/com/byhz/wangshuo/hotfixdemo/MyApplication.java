package com.byhz.wangshuo.hotfixdemo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Created by wangshuo on 2017/7/18.
 */

public class MyApplication extends Application{
    public static final String appId = "24550234-1";
    public static final String appSecret = "d7a6200914d49dbc2f918f3f65bb45ea";
    public static final String rsaSecret = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCimVCRArMEvASt5xLCxm2H8XD+X6tBEwEnWlMTK6rSqnxxhXbvcikhd1D88sm7Oa6+yhOVRsUEGle/I8AhXe6/WEgyB2HnKGYMxGEAAAXHT83b490939eCu2AOZNYaRg8cniGO+RhOlIAvXW7e5+yEBYh8SZB+JuFfh7fZ8WKUI47XubBUL6TMenl7Iz/JpnIwac4X5C8HoQ9t4hrUoMdGIRcsZ4LerCbhyOWcNPyB+HwJXH7I6Yqh5tWHGpPnbRHCJB9rAdUrsQyPCnkNXRV9AwyHQr1y4Hz41Bv1nmwG4c2z9kEHnn3cm7M8A9ya6ey+H4ly+Wa2to/TwVeLdVy9AgMBAAECggEAZMTvkW9C0yRTIhObJOsk5qNTi9JuqB3HEUSBR9hAPd860IAOfccahosTvjAZpdbR3ZwPFuyNHUXWK7dUU1mLpTppQ9gfbMs0s6ThGMbqzeM/iY9mhfo77eGCXmlP52iT5QB7e8gUsF6IMtKM68iAZDGprJ1K12PxOOs7ne5jUbrasw7dXIMzvP1eJnFdHPtng6cBgvIqAEpFuu8Mq9kbcDMCoy2bcJ6jjEIoJnHM3JwJgA/CP3L8WagnfnRGcW/p0nnWMYLh69yUKiLUjWLDxTlFB6Gj8iVLnVjcw3Ac/iFgnc5S+f1FAhZXp59QZzfvm9QxsEvDku0ewSsIgpc6AQKBgQDT27ogPoS55K3vZDbMr27ObjpGUsqjhXOLDCyWiSGpCRI/9JzekPRSyzildBITHZQWoy7Gg8E+62e3bpzW+cud5wg8Jk8Gm1pctZ3w9cqG3Wk3i6cZv2YXjnqyYeYnB+eXPDPLMwZaWoh1eSGQfSxVsEQv5aoGq0g5tX2qZAxxMQKBgQDEeiXhzMByZGNv5U9bqnnEVop2NdLR70Rr/ovelcjKk+WgeC5JjXC0gTk+CQNGe+JD13ji+9vuP7iK6xYWPh3tdlb62G62q8dISZ9d4uJexYm7kFCwB+37Gyf77eWMDcMBx8nRMiDX05a8Fbx6xGNW5Eiu/dImN5j76+FKIyAhTQKBgADgRLnDaGsYXfvCb1OTWiLyY71MtFfOwnaNu1V4BbKcPf4Ttd5ZvRjmjDpq63Q2yBj73cKmYtMuyAQDKhTdfZEOloMhKWx+kfYWLiwD6C18luXK5wr9Np/cT/eS3g/FQZjnf9NUR69x9mR99ryRN/C2xOFp0rUqWica8AUXWtHRAoGAMCBPaSjTNvH6HW2I68YtP3eHQP9cHYpDM0TNk/8SiPSDObRWejPIGJ7Sm6xvffNYcA+2j7rrm0dGd9ivt1EN2Z20HounSt122fEWtVOe6WHvo97stMGsfGahe6dBI/Y2/xemCrUBsHwmnvmgCl5SivUuDJclw7C4q3SS4asK8r0CgYBuwUThrqFwP9d4aFnYhLXB5i8CnPauv6rvH+SdDk0T0fUILDP1o0UC9zQAkfa6vKQ3e+7EolmDZFpaqCA5hzCdTSTByx3R9zGVzXIiCF178M4gjwq/rMjTuD4Yx0IX+wRC8vPb3ksTw01nfZB5+gT/5OWjls+Rcxr5SwdglVNwBQ==";

    public interface MsgDisplayListener {
        void handle(String msg);
    }

    public static MsgDisplayListener msgDisplayListener = null;
    public static StringBuilder cacheMsg = new StringBuilder();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        initHotfix();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initHotfix();
    }

    private void initHotfix() {
        String appVersion;
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            appVersion = "1.0.3";
        }

        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                .setEnableDebug(true)
                .setSecretMetaData(appId, appSecret, rsaSecret)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        String msg = new StringBuilder("").append("Mode:").append(mode)
                                .append(" Code:").append(code)
                                .append(" Info:").append(info)
                                .append(" HandlePatchVersion:").append(handlePatchVersion).toString();
                        if (msgDisplayListener != null) {
                            msgDisplayListener.handle(msg);
                        } else {
                            cacheMsg.append("\n").append(msg);
                        }
//                        SophixManager.getInstance().killProcessSafely();
//                        SophixManager.getInstance().cleanPatches();
                    }
                }).initialize();


//        初始化不用管，直管SophixManager.getInstance().queryAndLoadNewPatch();就可以对吧
//        SharedPreferences可以存下应用过的补丁号-->我们的回调里版本号都有吧
//        下面是判断是否需要调用queryAndLoadNewPatch()：
//        1、每次应用完补丁，用SharedPreferences存下补丁号，例：1.0.0应用了补丁5
//        2、每次启动时，去你们自己的后台请求一下，例：传1.0.0过去，返回1.0.0下最新的补丁版本6，这样之前存的一对比，就可以调用了queryAndLoadNewPatch()了；
//           如果返回了5，一样的，就不作处理
//        3、继续第2步骤，更新最新补丁之后，同样存下补丁号，用以重复步骤2
//        4、管理你们自己后台的接口，可以是一个表，存下每个应用版本下的补丁号，可自行输入控制，这个很简单，跟后台一说他就明白了，例：1.0.0下最新的是6；1.0.1下
//           最新的是9。后台可以只管写这个接口，其余的手动输入版本号可给你权限，自己去改表数据；最新的补丁号，没上传一个发布就知道的
    }
}
