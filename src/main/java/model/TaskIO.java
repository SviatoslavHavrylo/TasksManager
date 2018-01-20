package model;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sviatoslav_H on 17.12.2017.
 */
public class TaskIO {

     public static void write(TaskList tasks, OutputStream out) {
        try {
            ObjectOutputStream out2 = new ObjectOutputStream(out);
            out2.writeInt(tasks.size());
            for (Task task : tasks) {
                out2.writeObject(task);
            }
        } catch (IOException e) {
            System.out.println("Binary writing error");
        }
    }

    /**
     * Binary reading task from stream in tasklist
     * @param tasks
     * @param in
     */
    public static void read(TaskList tasks, InputStream in) {
        try (ObjectInputStream in2 = new ObjectInputStream(in)) {
            int n = in2.readInt();
            for (int i = 0; i < n; i++) {
                Task task = null;
                try {
                    task = (Task) in2.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("Binary reading error");
        }
    }

     public static void writeBinary(TaskList tasks, File file) throws IOException {
        BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(file));
        try {
            write(tasks, fileOut);
        } finally {
            fileOut.close();
        }        
    }

    public static void readBinary(TaskList tasks, File file) throws IOException {
        BufferedInputStream fileIn = new BufferedInputStream(new FileInputStream(file));
        try {
            read(tasks, fileIn);
        } finally {
            fileIn.close();
        }
    }

    public static void write(TaskList tasks, Writer out) throws IOException {
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(out));
        StringBuilder dataOut = new StringBuilder();
        int size = 0;
        int listSize = tasks.size();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        for (Task task : tasks) {
            dataOut.append("\"" + task.getTitle() + "\"");
            if (task.isRepeated()) {
                dataOut.append(" from ");
                dataOut.append("[" + dateFormat.format(task.getStartTime()) + "]");
                dataOut.append(" to ");
                dataOut.append("[" + dateFormat.format(task.getEndTime()) + "]");
                dataOut.append(" every ");
                int interval = task.getRepeatInterval();
                String stringInterval = minutesToStringTime(interval);
                dataOut.append(stringInterval);
            } else {
                dataOut.append(" at ");
                dataOut.append("[" + dateFormat.format(task.getTime()) + "]");
            }
            size++;
            dataOut.append(task.isActive() ? " active" : " inActive");
            dataOut.append(size == listSize ? "." : ";\n");
            printWriter.append(dataOut);
            printWriter.flush();
        }
        printWriter.close();
    }

    public static String minutesToStringTime(int t) {
        String result = "";
        int days = t / (60 * 24);
        int hours = (t - days * 60 * 24) / 60;
        int minutes = t - (days * 60 * 24) - (hours * 60);
        int seconds = t - (days * 60 * 60 * 24) - (hours * 60 * 60) - (minutes * 60);

        if (days > 0) {
            result = result + days + ending(days, " day");
        }
        if (hours > 0) {
            result = result + " " + hours + ending(hours, " hour");
        }
        if (minutes > 0) {
            result = result + " " + minutes + ending(minutes, " minute");
        }
        if (seconds > 0) {
            result = result + " " + seconds + ending(seconds, " second");
        }
        result = result.trim();
        return "[" + result + "]";
    }

    private static String ending(int time, String s) {
        if (time < 2) {
            return s;
        } else {
            return s + "s";
        }
    }

    public static void read(TaskList tasks, Reader in) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(in);
        String taskString;
        Task myTask;
        String title = "";
        Date dStart = null;
        Date dEnd = null;
        Date dTime = null;
        int interval = 0;
        boolean active = false;
        boolean repeated = false;
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");

        while ((taskString = br.readLine()) != null) {
            Pattern patTitle1 = Pattern.compile("from");
            Matcher mTitle = patTitle1.matcher(taskString);
            Pattern patTitle2 = Pattern.compile("at");
            Matcher mTitle2 = patTitle2.matcher(taskString);
            if (mTitle.find()) {
                title = taskString.substring(0, mTitle.start() - 1);
            } else {
                if (mTitle2.find()) {
                    title = taskString.substring(0, mTitle2.start() - 1);
                }
            }
            if (title.charAt(0)== '"') {title=title.substring(1, title.length());}
            if (title.charAt(title.length()-1)== '"') {title=title.substring(0, title.length()-1);}

            String start = "";
            Pattern patStart = Pattern.compile("from.+to");
            Matcher mStart = patStart.matcher(taskString);
            if (mStart.find()) {
                start = taskString.substring(mStart.start() + 6, mStart.end() - 4);
                dStart = date.parse(start);
                repeated = true;
            }

            String end = "";
            Pattern patEnd = Pattern.compile("to.+every");
            Matcher mEnd = patEnd.matcher(taskString);
            if (mEnd.find()) {
                end = taskString.substring(mEnd.start() + 4, mEnd.end() - 7);
                dEnd = date.parse(end);
                repeated = true;
            }


            String time = "";
            Pattern patTime = Pattern.compile("at.+]");
            Matcher mTime = patTime.matcher(taskString);
            if (mTime.find()) {
                time = taskString.substring(mTime.start() + 4, mTime.end() - 1);
                dTime = date.parse(time);
                repeated = false;
            }

            String days = "";
            String hours = "";
            String minutes = "";
            String seconds = "";

            Pattern pat = Pattern.compile("every.+day");
            Matcher m = pat.matcher(taskString);
            if (m.find()) {
                days = taskString.substring(m.start() + 7, m.end() - 4);
                interval = Integer.parseInt(days) * 24*60; //86400
            }
            Pattern pat2 = Pattern.compile("hour");
            Matcher m2 = pat2.matcher(taskString);
            if (m2.find()) {
                hours = taskString.substring(m2.start() - 3, m2.end() - 5);
                try {
                    interval = Integer.parseInt(hours) * 60; //3600
                } catch (NumberFormatException e) {
                    String cHours = taskString.substring(m2.start() - 2, m2.end() - 5);
                    interval += Integer.parseInt(cHours) * 60;
                }
            }
            Pattern pat3 = Pattern.compile("minute");
            Matcher m3 = pat3.matcher(taskString);
            if (m3.find()) {
                minutes = taskString.substring(m3.start() - 3, m3.end() - 7);
                try {
                    interval += Integer.parseInt(minutes) * 1; //60
                } catch (NumberFormatException e) {
                    String cMinutes = taskString.substring(m3.start() - 2, m3.end() - 7);
                    interval += Integer.parseInt(cMinutes) * 1;
                }
            }
            Pattern patSec = Pattern.compile("second");
            Matcher mSec = patSec.matcher(taskString);
            if (mSec.find()) {
                seconds = taskString.substring(mSec.start() - 3, mSec.end() - 7);
                try {
                    interval += Integer.parseInt(seconds);
                } catch (NumberFormatException e) {
                    String cSeconds = taskString.substring(mSec.start() - 2, mSec.end() - 7);
                    interval += Integer.parseInt(cSeconds)/60;
                }
            }

            /**
             * Active
             */
            Pattern patActive = Pattern.compile("inActive");
            Matcher mActive = patActive.matcher(taskString);
            if (mActive.find()) {
                active = false;
            }else{
                active = true;
            }


            /**
             * Create task
             */
            if (repeated) {
                myTask = new Task(title, dStart, dEnd, interval);
            } else myTask = new Task(title, dTime);
            if (active) {
                myTask.setActive(true);
            }
            tasks.add(myTask);
        }
        br.close();
}
    public static void writeText(TaskList tasks, File file) throws IOException {
        PrintWriter dataOut = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        try {
            write(tasks, dataOut);
        } finally {
            dataOut.close();
        }
    }

    public static void readText(TaskList tasks, File file) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            read(tasks, br);
        } finally {
            br.close();
        }
    }
}