// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hello.proto

package dev.appkr.grpcdemo;

public interface HelloReplyOrBuilder extends
    // @@protoc_insertion_point(interface_extends:grpcdemo.HelloReply)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string message = 1;</code>
   * @return The message.
   */
  java.lang.String getMessage();
  /**
   * <code>string message = 1;</code>
   * @return The bytes for message.
   */
  com.google.protobuf.ByteString
      getMessageBytes();

  /**
   * <code>int32 luckyNumber = 2;</code>
   * @return The luckyNumber.
   */
  int getLuckyNumber();
}
