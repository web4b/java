package ch.web4b.hibernate.helper;

import java.io.File;
import java.net.URL;

import javax.persistence.Entity;

import org.hibernate.cfg.Configuration;

/**
 * A simple Helper class which gives a method for dynamically loading classes from a package
 * and add as annotated class to hibernate config
 * @author Enrico Buchs <e.buchs@web4b.ch>
 * @version 1.0
 */
public class AnnotatedClassLoader {
	
	/**
	 * this method allows to append all classes which includes an
	 * {@link:javax.persistence.Entity} Annotation to the hibernate config from
	 * a specific package
	 * 
	 * @author Enrico Buchs <e.buchs@web4b.ch>
	 * @param cfg
	 *            org.hibernate.cfg.Configuration the hibernate configuration
	 *            object
	 * @param packagename
	 *            the packagename which includes the entityclasses
	 */
	public static void appendAnnotatedClasses(Configuration cfg, String packagename) {
		String pkn = packagename.replace(".", "/");
		URL cl = Configuration.class.getResource("/" + pkn);

		File dir = new File(cl.getFile());

		for (File file : dir.listFiles()) {
			Class<?> cls = null;
			try {
				String[] s = file.getName().split("\\.");

				cls = Class.forName(packagename + "." + s[0]);
				if (cls.isAnnotationPresent(Entity.class)) {
					cfg.addAnnotatedClass(cls);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
