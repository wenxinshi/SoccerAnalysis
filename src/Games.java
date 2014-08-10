import java.awt.FileDialog;
import java.io.File;
import javax.swing.JFrame;

public class Games {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		System.setProperty("apple.awt.fileDialogForDirectories", "true");
		FileDialog d = new FileDialog(frame);
		d.setVisible(true);

		frame.dispose();
		if (d.getDirectory() == null)
			return;

		String path = d.getDirectory() + d.getFile() + "/";
		File fold = new File(path);
		File[] files = fold.listFiles();
		for (File file : files) {
			if (file.getName().matches(".*xls$")) {
				GameStats game = new GameStats();
				game.parse(file.toString());
				game.CaryDerivation();
				file.renameTo(new File(path + game.name + ".xls"));
			}
		}
	}
}
