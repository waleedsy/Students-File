import  java.io.*;
import java.nio.charset.StandardCharsets;

class Students {

    static File file;

    static RandomAccessFile students;


//    OptimizedRandomAccessFile students = new OptimizedRandomAccessFile(file, "rw");

    static boolean found = false;
    static byte[] std_id = new byte[4];
    //byte[] record = new byte[55];
    static String idno = null;
    static String std_rec = null;
    static String[] fields;

    public Students(String filename) throws IOException
    {
        file = new File (filename);

        try {
            students = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        while (students.getFilePointer() <= students.length() -2)
        {
            students.read(std_id, 0, 4);
            idno = new String(std_id);

            if (idno.equals("6235"))
            {
                students.seek(students.getFilePointer() - 4);
                std_rec = students.readLine();
                fields = std_rec.split(",");
                fields[2] = "3.45";
                std_rec = String.join(",", fields);
                //students.read(record, 0, 53);
//                students.seek(students.getFilePointer() + 32);
//                students.write("3.50".getBytes("UTF-8"));
//                students.seek(students.getFilePointer() - 40);
//                students.read(record, 0, 53);
//                std_rec = new String(record);
//
                students.seek(students.getFilePointer() - 55);
                students.write(std_rec.getBytes(StandardCharsets.UTF_8));

                found = true;
            }
            if (found)
            {
                break;
            }

            students.seek(students.getFilePointer() + 51);
        }

        System.out.println(std_rec);

        students.close();
    }

    public static void modify (String stdid, String field, String new_value) throws IOException {

       stdid = std_rec.split(",")[0];

        if (stdid.equals(idno))
        {
            students.seek(students.getFilePointer() + 4);
            field = String.valueOf(fields);
            field.split(",");
            students.seek(students.getFilePointer());
            students.write(new_value.getBytes("UTF-8"));
        }
    }

    public static void insert (String stdid, String name, String cgpa, String date, String gender) throws IOException {
        if (students.readLine() != null)
        {
            students.seek(students.getFilePointer() -55);
            students.write(Integer.parseInt(stdid));
            students.writeChars(name);
            students.writeDouble(Double.parseDouble(cgpa));
            students.write(date.getBytes("UTF-8"));
            students.writeChars(gender);
        }
    }

    public static void delete(String stdid) throws IOException
    {
        String line;

        if (stdid.equals(idno))
        {
            while ((line = students.readLine()) != null)
            {
                students.seek(0);
                line = null;
                students.writeChars(line);
            }
       }
    }

    public static void display() throws IOException {

        while (students.readLine() != null)
        {
            students.seek(students.getFilePointer() -55);

            for (String f: fields)
            {
                std_rec = students.readLine();
                std_rec = students.readLine();
                fields = std_rec.split(",");

                System.out.println(fields + "\t");
            }
        }
    }

    public static void stats() throws IOException
    {
        String path = "/Users/waleed/Students example/stats.txt";
        File file = new File (path);
        FileWriter writer = new FileWriter(file);
        String data;

        while ((data = students.readLine()) != null)
        {
            students.seek(students.getFilePointer() + 55);
            fields = data.split(",");
            data = String.join(",", fields);
        }
    }
}