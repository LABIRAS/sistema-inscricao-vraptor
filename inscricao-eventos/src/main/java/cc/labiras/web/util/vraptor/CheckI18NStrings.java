package cc.labiras.web.util.vraptor;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.zip.ZipException;

public class CheckI18NStrings {
	/**
	 * for all elements of java.class.path get a Collection of resources Pattern pattern = Pattern.compile(".*"); gets
	 * all resources
	 * 
	 * @param patternStr the pattern to match
	 * @return the resources in the order they are found
	 * @throws IOException
	 * @throws ZipException
	 */
	public static List<File> getResources(final String patternStr) throws ZipException, IOException {
		final List<File> retval = new LinkedList<File>();
		final String classPath = System.getProperty("java.class.path", ".");
		final String[] classPathElements = classPath.split(File.pathSeparator);
		
		for (final String element : classPathElements) {
			retval.addAll(getResources(element, patternStr));
		}
		
		return retval;
	}
	
	private static List<File> getResources(final String element, final String patternStr) throws ZipException, IOException {
		final Pattern pattern = Pattern.compile(patternStr);
		final List<File> retval = new LinkedList<File>();
		final File file = new File(element);
		
		if (file.isDirectory()) {
			retval.addAll(getResourcesFromDirectory(file, pattern));
		}
		
		return retval;
	}
	
	private static List<File> getResourcesFromDirectory(final File directory, final Pattern pattern) throws IOException {
		final List<File> retval = new LinkedList<File>();
		final File[] fileList = directory.listFiles();
		for (final File file : fileList) {
			if (file.isDirectory()) {
				retval.addAll(getResourcesFromDirectory(file, pattern));
			} else {
				final String fileName = file.getName();
				final boolean accept = pattern.matcher(fileName).matches();
				if (accept) {
					retval.add(file);
				}
			}
		}
		return retval;
	}
	
	public static void main(final String[] args) throws ZipException, IOException {
		final List<File> mainProperties = getResources("messages.properties");
		final List<File> subProperties = getResources("messages(_\\w{2,3})*\\.properties");
		
		final Properties main = new Properties();
		main.load(new FileReader(mainProperties.get(0)));
		
		for (final File file : subProperties) {
			final Properties sub = new Properties();
			sub.load(new FileReader(file));
			
			for (final Object key : main.keySet()) {
				if (!sub.containsKey(key)) {
					System.out.println("Key [" + key + "] from [" + mainProperties.get(0).getName() + "] missing in [" + file.getName() + "]");
				}
			}
			
			for (final Object key : sub.keySet()) {
				if (!main.containsKey(key)) {
					System.out.println("Key [" + key + "] from [" + file.getName() + "] missing in [" + mainProperties.get(0).getName() + "]");
				}
			}
		}
	}
	// public static void main(final String[] args) throws IOException {
	// final Properties main = new Properties();
	// main.load(ClassLoader.getSystemResourceAsStream("messages.properties"));
	//
	// ClassLoader.getSystemClassLoader().get
	//
	// final Enumeration<URL> messages = ClassLoader.getSystemResources("messages");
	// while (messages.hasMoreElements()) {
	// System.out.println(messages.nextElement());
	// }
	//
	// System.out.println("--------------");
	//
	// System.out.println(main);
	// }
}
