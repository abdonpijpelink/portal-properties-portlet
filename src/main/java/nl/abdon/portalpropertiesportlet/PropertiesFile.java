package nl.abdon.portalpropertiesportlet;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PropertiesFile
{
	private String _content;
	private String _displayName;
	private boolean _editable;
	private boolean _existant;
	private String _filename;
	private String _fullPath;
	private boolean _inLRHome;
	private boolean _inWriteableDirectory;

	public PropertiesFile(String displayName, String filename, boolean inLRHome)
	{
		_displayName = displayName;
		_filename = filename;
		_inLRHome = inLRHome;

		if (inLRHome)
		{
			_fullPath = PropsUtil.get(PropsKeys.LIFERAY_HOME) + File.separator + filename;
		}
		else
		{
			_fullPath = com.liferay.portal.util.PortalUtil.getPortalLibDir() + "../classes/" + filename;
		}
		
		_existant = Files.exists(Paths.get(_fullPath));
		
		if (_existant)
		{
			_editable = _inLRHome && Files.isWritable(Paths.get(_fullPath));
			
			try
			{
				_fullPath = Paths.get(_fullPath).normalize().toString();
				byte[] encoded = Files.readAllBytes(Paths.get(_fullPath));
				_content = new String(encoded, Charset.defaultCharset());
			}
			catch (Exception e)
			{
				// Should not happen
				_content = "Unable to read file " + _fullPath;
				_editable = false;
			}
		}
		else
		{
			_editable = false;
		}
		
		_inWriteableDirectory = _inLRHome && Files.isWritable(Paths.get(PropsUtil.get(PropsKeys.LIFERAY_HOME)));
	}

	public String getContent()
	{
		return _content;
	}
	
	public String getDisplayName()
	{
		return _displayName;
	}

	public String getFilename()
	{
		return _filename;
	}

	public String getFullPath()
	{
		return _fullPath;
	}

	public boolean isEditable()
	{
		return _editable;
	}
	
	public boolean isExistant()
	{
		return _existant;
	}

	public boolean isInLRHome()
	{
		return _inLRHome;
	}

	public boolean isInWriteableDirectory()
	{
		return _inWriteableDirectory;
	}
	
	public String toString()
	{
		return _displayName;
	}
}
