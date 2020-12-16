package net.hyntech.usual.jpush;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.service.JPushMessageReceiver;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.IntentUtils;

import net.hyntech.baselib.utils.LogUtils;
import net.hyntech.usual.R;
import net.hyntech.usual.ui.activity.EbikeErrorActivity;
import net.hyntech.usual.ui.activity.SplashActivity;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import static android.content.Context.NOTIFICATION_SERVICE;
public class JPushReceiver extends JPushMessageReceiver {

    /**
     * 连接极光服务器
     */
    @Override
    public void onConnected(Context context, boolean b) {
        super.onConnected(context, b);
        LogUtils.logGGQ("连接极光服务器->>" + "onConnected");
    }

    /**
     * 注册极光时的回调
     */
    @Override
    public void onRegister(Context context, String s) {
        super.onRegister(context, s);
        LogUtils.logGGQ("注册极光时的回调->>" + "onRegister" + s);
    }

    //


    /**
     * 打开了通知
     * notificationMessage.notificationExtras(附加字段)的内容处理代码
     * 比如打开新的Activity， 打开一个网页等..
     */
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        LogUtils.logGGQ( "打开了通知->>"+ notificationMessage.notificationExtras );
        try{
            //打开自定义的Activity
            Intent intent = new Intent(context, EbikeErrorActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            //通知的话这段跳转代码即使不写，也会默认跳到主界面
            context.startActivity(intent);
            LogUtils.logGGQ( "-打开了通知-");
        }catch (Exception e){
            e.fillInStackTrace();
            LogUtils.logGGQ( "-打开了通知异常-"+e.fillInStackTrace().getMessage());
        }
    }

    /**
     * 接收到推送下来的自定义消息
     * 自定义消息不是通知，默认不会被SDK展示到通知栏上，极光推送仅负责透传给SDK。其内容和展示形式完全由开发者自己定义。
     * 自定义消息主要用于应用的内部业务逻辑和特殊展示需求
     */
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);
        // 收到消息 显示通知
        LogUtils.logGGQ( "接收到推送下来的自定义消息->>"+ customMessage.message);
        //自定义消息如果不写展示界面的话，默认看不到
//        processCustomMessage(context, customMessage.message);
    }


    //通知
    private void processCustomMessage(Context context, String message) {

        String channelID = "1";
        String channelName = "channel_name";

        // 跳转的Activity
        Intent intent = new Intent(context, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        //适配安卓8.0的消息渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(context, channelID);

        notification.setAutoCancel(true)
                .setContentText(message)
                .setContentTitle("新消息")
                .setSmallIcon(R.mipmap.ic_launcher_usual)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent);

        notificationManager.notify((int) (System.currentTimeMillis() / 1000), notification.build());
    }




    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        String actionExtra = intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA);
        LogUtils.logGGQ("[onMultiActionClicked] 用户点击了通知栏按钮" + actionExtra);
    }


    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context,jPushMessage);
        super.onTagOperatorResult(context, jPushMessage);
    }
    @Override
    public void onCheckTagOperatorResult(Context context,JPushMessage jPushMessage){
        TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context,jPushMessage);
        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    /**
     * 注册以及解除注册别名时回调
     */
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context,jPushMessage);
        super.onAliasOperatorResult(context, jPushMessage);
        LogUtils.logGGQ("注册以及解除注册别名时回调->>" + jPushMessage.toString());
    }

    /**
     * 设置手机号码返回的消息结果体
     */
    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }


    /**
     * 接收到推送下来的通知
     * 可以利用附加字段（notificationMessage.notificationExtras）来区别Notication,指定不同的动作,附加字段是个json字符串
     * 通知（Notification），指在手机的通知栏（状态栏）上会显示的一条通知信息
     */
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        LogUtils.logGGQ( "接收到推送下来的通知->>" + notificationMessage.toString());
    }

}
