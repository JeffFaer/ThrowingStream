package throwing.stream.intermediate;


public interface ThrowingBaseStreamIntermediate<S extends ThrowingBaseStreamIntermediate<S>> {
  public S sequential();

  public S parallel();

  public S unordered();

  public S onClose(Runnable closeHandler);

  public boolean isParallel();
}
