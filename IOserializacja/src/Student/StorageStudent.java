package Student;

import java.io.*;

public class StorageStudent {

    private String fileName;
    private File file;
    FileInputStream fileInputStream;
    FileOutputStream fileOutputStream;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    RandomAccessFile randomAccessFile;

    public StorageStudent(String fileName) throws IOException{
        this.fileName = fileName;
        file = new File(fileName);
        if(!file.isFile()){
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            dataOutputStream = new DataOutputStream(fileOutputStream);
            for (int i=0;i<20;++i){
                //dataOutputStream.writeInt(12*20);
                dataOutputStream.writeInt(0);
                dataOutputStream.writeInt(0);
            }
            dataOutputStream.flush();
            dataOutputStream.close();
            fileOutputStream.close();
        }
    }

    public void setImage(byte[] img, int poz) throws IOException {
        try {
            randomAccessFile = new RandomAccessFile(file,"rw");
            fileInputStream = new FileInputStream(file);
            dataInputStream = new DataInputStream(fileInputStream);

            dataInputStream.skipBytes(poz*12);
            int offset = dataInputStream.readInt();
            int size = dataInputStream.readInt();

            randomAccessFile.seek(poz*12+4);
            randomAccessFile.writeInt(img.length);
            for (int i=poz; i<20;++i){
                randomAccessFile.seek(i*12);
                int off=randomAccessFile.readInt();
                randomAccessFile.seek(i*12);
                randomAccessFile.writeInt(off+img.length-size);
            }

            randomAccessFile.seek(offset);
            offset-=12*poz+8;
            dataInputStream.skip(offset);
            dataInputStream.skip(size);
            byte[] b=new byte[dataInputStream.available()];
            dataInputStream.read(b);
            randomAccessFile.write(img);
            randomAccessFile.write(b);


            randomAccessFile.close();
            dataInputStream.close();
            fileInputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public byte[] getImage(int poz) throws IOException{
        fileInputStream=new FileInputStream(file);
        dataInputStream=new DataInputStream(fileInputStream);
        dataInputStream.skip(poz*12);
        int offset=dataInputStream.readInt();
        int size=dataInputStream.readInt();
        offset-=12*poz+8;
        dataInputStream.skip(offset);
        byte[] image=new byte[size];
        int readBytes=dataInputStream.read(image);
        fileInputStream.close();
        dataInputStream.close();
        if (readBytes!=size)
            throw new IOException("wrong image size");
        return image;
    }


    public void close() throws IOException{
        if (fileInputStream!=null)
            fileInputStream.close();
        if (dataInputStream!=null)
            dataInputStream.close();
        if (dataOutputStream!=null)
            dataOutputStream.close();
        if (fileOutputStream!=null)
            fileOutputStream.close();
    }

    public void open() throws IOException{
        if (file==null)
            file=new File(fileName);
        if (!file.isFile()){
            file.createNewFile();
            fileOutputStream=new FileOutputStream(file);
            dataOutputStream=new DataOutputStream(fileOutputStream);
            for (int i=0;i<20;++i){
              //  dataOutputStream.writeInt(12*20);
                dataOutputStream.writeInt(0);
                dataOutputStream.writeInt(0);
            }


            dataOutputStream.flush();
            dataOutputStream.close();
            fileOutputStream.close();

        }
    }



    

}
