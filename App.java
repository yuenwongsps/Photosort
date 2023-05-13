
import java.io.File;
import java.io.FileFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class App {

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
 
    private String mediaFilesPath;

    public App(){
       prepareGUI();
    }
   

public static File[] getImagesFileListInDir(String path) {
//return list of path for supported image file in given path

  File f = new File(path);

  //option for faster file list, but keep it simple in this version
  //  DirectoryStream ds = java.nio.file.Files.newDirectoryStream(path);

  
  //Extends abstract class FilterFilter with Lamda expression method because
  //the old way to extends abstract class requires a new class file
  FileFilter supportedFileFilter = (file) -> {
  //file filter must return boolean
  //https://howtodoinjava.com/java/io/java-filefilter-example/#:~:text=Java%20FileFilter%20is%20a%20filter,lambda%20expression%20or%20method%20reference.

   System.out.println(file.getName());
   var result = file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".tiff") || file.getName().toLowerCase().endsWith(".jpeg"); 

    return result;

  };

  var files = f.listFiles(supportedFileFilter);

  return files;

}

public static void MoveMediaFilesByYearDate(String srcPath, String destPath){

        
//#1. select a folder with media files
//#2. for each media file, get metadata
//#3.   create a year/month folder if it does not exist
//#4.   move the file to the new folder
//https://www.tutorialspoint.com/moving-a-file-from-one-directory-to-another-using-java


String imageFolder = srcPath;

for(File imageFileOjb: getImagesFileListInDir(imageFolder)) {


    
File sourceFile = new File(imageFileOjb.getAbsolutePath());

MediaFileInfo mfInfo = new MediaFileInfo();
mfInfo.setFileShortName(sourceFile.getName());
mfInfo.setFileAbsPath(sourceFile.getAbsolutePath());

try {
    Path p = Paths.get(sourceFile.getAbsolutePath());
    BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);

    mfInfo.setCreatedTime(attr.creationTime().toString());

    System.out.println(mfInfo.toString());

        File directory = new File(imageFolder + "/" + mfInfo.Year() + "/" + mfInfo.Month());
        if (! directory.exists()){
            directory.mkdirs();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }

        Path MoveResult = null;
        try {
            MoveResult = Files.move(Paths.get(sourceFile.getAbsolutePath()), Paths.get(destPath + "/" + mfInfo.Year() + "/" + mfInfo.Month()+"/"+mfInfo.getFileShortName()) );
        } catch (IOException e) {
            System.out.println("Exception while moving file: " + e.getMessage());
        }
        if(MoveResult != null) {
            System.out.println("File moved successfully.");
        }else{
            System.out.println("File movement failed.");
        }






    //read file attributes
    //    readFileAttr(sourceFile);

    //read image file metadata
    //readImageMeta(sourceFile);

}

    catch  (IOException e) {
        System.out.println("Exception while checking folder : " + e.getMessage());
    }

}

}

private void prepareGUI(){
    mainFrame = new JFrame("Photo Sort");
    mainFrame.setSize(400,800);
    mainFrame.setLayout(new GridLayout(3, 1));

    headerLabel = new JLabel("",JLabel.CENTER );
    statusLabel = new JLabel("",JLabel.CENTER);        
    statusLabel.setSize(350,100);
    
    mainFrame.addWindowListener(new WindowAdapter() {
       public void windowClosing(WindowEvent windowEvent){
          System.exit(0);
       }        
    });    
    controlPanel = new JPanel();
    controlPanel.setLayout(new FlowLayout());

    mainFrame.add(headerLabel);
    mainFrame.add(controlPanel);
    mainFrame.add(statusLabel);
    mainFrame.setVisible(true); 
    
    
    headerLabel.setText("Group your photo:"); 


     //a button to select a folder from folder dialog

    //a button to trigger sorting file into folders by year/month

    JButton browseButton = new JButton("Browse", null);
    JButton byDateButton = new JButton("Group By Date", null);


    final JLabel selectedPath = new JLabel(""); 

    browseButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showOpenDialog(mainFrame);
            if(option == JFileChooser.APPROVE_OPTION){
               File file = fileChooser.getSelectedFile();
               mediaFilesPath = file.getAbsolutePath();
               selectedPath.setText("Folder Selected: " + file.getName());
            }else{
                selectedPath.setText("Folder selection canceled");
                mediaFilesPath = "";
            }
        }
    });

    byDateButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            File directory = new File(mediaFilesPath);
        if (directory.exists()){

            statusLabel.setText("Processing files");
            MoveMediaFilesByYearDate(mediaFilesPath, mediaFilesPath);

            statusLabel.setText("Done");
        
        }
        else {
            System.out.print("error in path selection:" + mediaFilesPath);
            statusLabel.setText("Media file folder is invalid. Please try again.");
        }

            
        }
    });

    controlPanel.add(selectedPath);

    controlPanel.add(browseButton);

    controlPanel.add(byDateButton);

   
 }

private void Show() {
    mainFrame.setVisible(true); 

}

    public static void main(String[] args) throws IOException {
    
      
        App app = new App();  
        app.Show();
        

    }

}



