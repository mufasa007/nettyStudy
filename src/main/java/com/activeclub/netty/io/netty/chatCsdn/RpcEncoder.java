package com.activeclub.netty.io.netty.chatCsdn;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author zjk
 * @date 2022/3/22
 * @descript
 * @since V1.0.0
 */
public class RpcEncoder extends MessageToByteEncoder {

  private Class<?> target;

  public RpcEncoder(Class target) {
    this.target = target;
  }

  @Override
  protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
    if(target.isInstance(msg)){
      byte[] data = JSON.toJSONBytes(msg);
      out.writeInt(data.length);
      out.writeBytes(data);
    }
  }
}
