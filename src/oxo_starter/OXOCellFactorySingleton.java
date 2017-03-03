package oxo_starter;

/* 
 * This class implements two patterns that make good sense on the OXO game:
 * a) factory
 * b) singleton
 * Singleton & Factory make good candidate for combination of two patterns in one class, because
 * Factory instantiates object of particular type for any client in the application, and thus it makes 
 * sense to turn it into a Singleton, especially if there's some more expensive operation (like
 * reading cfg file) involved in the actual concrete Factory class instantiation. 
 */
public class OXOCellFactorySingleton {

	private OXOCellFactorySingleton() {
		// TODO Auto-generated constructor stub
	}
	/*
	 * There's always small problem with Singleton pattern in multithreaded application - thread safe access
	 * and instantiation to the singleton instance, typically there would be synchronized access to 
	 * getInstance() method. This has big performance impact due to synch. lock acquire overhead.
	 * 
	 * Cheaper solution is to use static inner class like this, because Java guarantees the access only to fully 
	 * instantiated objects in this case, for all threads, thus the sync access is guaranteed.
	 * Disadvantage is that you cannot handle exceptions (if any) that will thrown in OXOCellFactorySingleton
	 * constructor.  
	 */
	private static class LazyHolder {
        private static final OXOCellFactorySingleton INSTANCE = new OXOCellFactorySingleton();
	}

	public static OXOCellFactorySingleton getInstance() {
	        return LazyHolder.INSTANCE;
	}
	
	public OXOCell createCell(GameModelDecorator decorator, int id) {
		int cellSize = decorator.getClass().getName().contains("SimpleGameModelDecorator") ? 10 : 1;
		return new OXOCell(id, cellSize, decorator);
	}

}
