package com.activeclub.netty.io.netty.chatCsdn;

import com.activeclub.netty.io.netty.RpcRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.List;

/**
 * @author zjk
 * @date 2022/3/22
 * @descript
 * @since
 */
public class RpcDecoder extends ByteToMessageDecoder {

  private Class<?> target;

  public RpcDecoder(Class target) {
    this.target = target;
  }

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    if(in.readableBytes()<4){
      return;
    }
    in.markReaderIndex();
    int dataLength = in.readInt();

    if(in.readableBytes()<dataLength){
      in.resetReaderIndex();
      return;
    }
    byte[] data = new byte[dataLength];
    in.readBytes(data);

    Object obj = JSON.parseObject(data, target);
    out.add(obj);
  }
}
