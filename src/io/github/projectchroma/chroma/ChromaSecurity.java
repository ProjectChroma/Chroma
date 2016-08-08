package io.github.projectchroma.chroma;

import java.io.File;
import java.io.FilePermission;
import java.lang.reflect.ReflectPermission;
import java.security.Permission;
import java.util.PropertyPermission;

import sun.security.util.SecurityConstants;

public class ChromaSecurity extends SecurityManager{
	protected ChromaSecurity(){}
	@Override
	public void checkExec(String cmd){
		checkExecImpl(cmd);
		super.checkExec(cmd);
	}
	private void checkExecImpl(String cmd){
		throw new SecurityException("Processes may not be started");//No need for anything to start a process
	}
	@Override
	public void checkRead(String file){
		checkReadImpl(file);
		super.checkRead(file);
	}
	private void checkReadImpl(String file){
		if(isSecurityExempt()) return;
		if(file.startsWith(System.getProperty("java.home"))) return;//Accessing java files
		if(!isAccessible(file)) throw new SecurityException(file + " is outside of game dir and cannot be read");
	}
	@Override
	public void checkWrite(String file){
		checkWriteImpl(file);
		super.checkWrite(file);
	}
	private void checkWriteImpl(String file){
		if(isSecurityExempt()) return;
		if(!isAccessible(file)) throw new SecurityException(file + " is outside of game dir and cannot be written");
	}
	@Override
	public void checkDelete(String file){
		checkDeleteImpl(file);
		super.checkDelete(file);
	}
	private void checkDeleteImpl(String file){
		if(isSecurityExempt()) return;
		if(!isAccessible(file)) throw new SecurityException(file + " is outside of game dir and cannot be deleted");
	}
	private boolean isAccessible(String file){
		String path = new File(file).getAbsolutePath();
		String parent = new File("").getAbsolutePath();
		return path.startsWith(parent);
	}
	@Override
	public void checkPermission(Permission perm){
		if(perm instanceof ReflectPermission && perm.getName().equals("suppressAccessChecks")) return;//Allow calling setAccessible(true)
		else if(perm instanceof RuntimePermission && perm.getName().startsWith("accessClassInPackage."))
			if(isSecurityExempt(perm.getName().substring(21))) return;//.substring(21) == class name to access
		else if(perm instanceof RuntimePermission && perm.getName().equals(SecurityConstants.CHECK_MEMBER_ACCESS_PERMISSION)) return;//Allow calling getDeclaredMethods() et al
		else if(perm instanceof FilePermission){
			FilePermission fp = (FilePermission)perm;
			String fileName = fp.getName();
			for(String action : fp.getActions().split(",")){
				if(action.equals(SecurityConstants.FILE_READ_ACTION)) checkReadImpl(fileName);
				else if(action.equals(SecurityConstants.FILE_WRITE_ACTION)) checkWriteImpl(fileName);
				else if(action.equals(SecurityConstants.FILE_EXECUTE_ACTION)) checkExecImpl(fileName);
				else if(action.equals(SecurityConstants.FILE_DELETE_ACTION)) checkDeleteImpl(fileName);
				else if(action.equals(SecurityConstants.FILE_READLINK_ACTION)) checkReadImpl(fileName);
				else throw new SecurityException("Unknown FilePermission action: " + action);
			}
		}else if(perm instanceof PropertyPermission) return;//Allow calling System.getProperty()
		else if(isSecurityExempt()) return;
		else{
			new Exception(String.valueOf(perm)).printStackTrace(System.out);
			super.checkPermission(perm);
		}
	}
	private final boolean isSecurityExempt(){
		for(Class<?> clazz : getClassContext()){//Every class in the call stack must be exempt for this to pass
			if(!isSecurityExempt(clazz.getName())) return false;
		}
		return true;//Every class in the call stack must be security-exempt to allow security-exempt action
	}
	public static final boolean isSecurityExempt(String className){
		return className.startsWith("java.") || className.startsWith("javax.") || className.startsWith("sun.") || className.startsWith("com.sun.")
				|| className.startsWith("io.github.projectchroma.chroma.")
				|| className.startsWith("org.lwjgl.") || className.startsWith("org.newdawn.slick.")
				|| className.startsWith("com.google.gson.");
	}
}
