package aop03;

public class ExecTimeCalculator implements Calculator{
	
	private Calculator delegate;
	
	public ExecTimeCalculator (Calculator delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public long factorial(long n) {
		long start = System.nanoTime();
		long result = delegate.factorial(n);
		long end = System.nanoTime();
		System.out.printf("%s.factorial(%d):수행시간%d\n", 
								delegate.getClass().getSimpleName(),
								n,
								(end-start));
		return result;
	}
}
