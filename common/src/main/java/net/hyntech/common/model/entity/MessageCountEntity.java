package net.hyntech.common.model.entity;

import net.hyntech.baselib.http.BaseResponse;

public class MessageCountEntity extends BaseResponse<MessageCountEntity> {
    private String messageCount;

    public String getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(String messageCount) {
        this.messageCount = messageCount;
    }
}
