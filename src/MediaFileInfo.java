

public class MediaFileInfo {
    private String fileShortName;
    private String fileAbsPath;
    private String createdTime;


    public String getFileShortName () {
        return fileShortName;
    }
    public void setFileShortName(String data) {
        fileShortName = data;
    }
    public String getFileAbsPath() {
        return fileAbsPath;   
    }
    public void setFileAbsPath(String data) {
        fileAbsPath = data;
    }
    public String getCreatedTime() {
        return createdTime.toString();
    }
    public void setCreatedTime(String data) {
        //2022-10-29T18:28:17Z
        //we only need the date section
        createdTime = data;
    }

    public String Year() {
        if ( createdTime.length() > 0 )
            return createdTime.substring(0, 4);
        else
            return "";
    }
    public String Month() {
        if (createdTime.length() > 0)
            return createdTime.substring(5, 7);
        else
            return "";

    }
    public String Day() {
        if (createdTime.length() > 0) {
            return createdTime.substring(8, 10);

        }
        else
            return "";
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MediaFileInfo{");
        sb.append("ShortName=").append(this.getFileShortName()).append(" ");
        sb.append("FileAbsPath=").append(this.getFileAbsPath()).append(" ");;
        sb.append("CreatedTime=").append(this.getCreatedTime()).append(" ");;
        sb.append("Year=").append(this.Year()).append(" ");
        sb.append("Month=").append(this.Month()).append(" ");
        sb.append("Day=").append(this.Day()).append(" ");
        sb.append("}");
        return sb.toString();

    }

}
