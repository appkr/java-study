package dev.appkr.springdata.objectdiff;

public interface Copyable extends Cloneable {
  Copyable copy() throws CloneNotSupportedException;
}
