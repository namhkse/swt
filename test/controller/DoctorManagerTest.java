package controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Scanner;
import model.Doctor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DoctorManagerTest {

    Doctor d1;
    Doctor d2;
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();

    public DoctorManagerTest() {
        d1 = new Doctor("Aqua", "Aqua specialization", 0, "aqua@email.com", "0000-111-222", LocalDate.of(2022, Month.MARCH, 1));
        d2 = new Doctor("Rius", "Rius specialization", 2, "rius@email.com", "0000-111-333", LocalDate.of(2021, Month.MARCH, 1));
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outStream));
    }

    @After
    public void tearDown() {
    }

    @Test
    public void printMenu_ExpectSuccess() throws IOException {
        DoctorManager dm = new DoctorManager();
        dm.printMenu();
        outStream.flush();
        String printedOutput = new String(outStream.toByteArray());
        String[] outputData = printedOutput.split(System.getProperty("line.separator"));
        assertEquals("0. List all doctor", outputData[0]);
        assertEquals("1. Create a new doctor", outputData[1]);
        assertEquals("2. Edit doctor's information", outputData[2]);
        assertEquals("3. Find by ID", outputData[3]);
        assertEquals("4. Find by name", outputData[4]);
        assertEquals("5. Delete by ID", outputData[5]);
        assertEquals("6. Sort by date of birth", outputData[6]);
        assertEquals("7. Exit", outputData[7]);
    }

    @Test
    public void editFullName_AbnormalCase_MoreThan50Character_ExpectError() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        doctor.setFullName("abc");
        String fullName = "11111111111111111111111111111111111111111111111111111111\n";
        String expected = "Enter Full Name: Name must be not empty and length <= 50";

        ByteArrayInputStream in = new ByteArrayInputStream(fullName.getBytes());
        instance.changeInputStream(in);
        try {
            instance.editName(doctor);
        } catch (Exception ex) {
        }
        outStream.flush();
        String printedOutput = new String(outStream.toByteArray());
        String[] outputData = printedOutput.split(System.getProperty("line.separator"));
        assertEquals(expected, outputData[0]);
    }

    @Test
    public void editFullName_AbnormalCase_Empty_ExpectError() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        doctor.setFullName("abc");
        String fullName = "\n";
        String expected = "Enter Full Name: Name must be not empty and length <= 50";

        ByteArrayInputStream in = new ByteArrayInputStream(fullName.getBytes());
        instance.changeInputStream(in);
        try {
            instance.editName(doctor);
        } catch (Exception ex) {
        }
        outStream.flush();
        String printedOutput = new String(outStream.toByteArray());
        String[] outputData = printedOutput.split(System.getProperty("line.separator"));
        assertEquals(expected, outputData[0]);
    }

    @Test
    public void editFullName_AbnormalCase_TrailingSpace_ExpectError() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        doctor.setFullName("abc");
        String fullName = "          \n";
        String expected = "Enter Full Name: Name must be not empty and length <= 50";

        ByteArrayInputStream in = new ByteArrayInputStream(fullName.getBytes());
        instance.changeInputStream(in);
        try {
            instance.editName(doctor);
        } catch (Exception ex) {
        }
        outStream.flush();
        String printedOutput = new String(outStream.toByteArray());
        String[] outputData = printedOutput.split(System.getProperty("line.separator"));
        assertEquals(expected, outputData[0]);
    }

    @Test
    public void editFullName_NormalCase_ExpectSuccess() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        doctor.setFullName("abc");
        String fullName = "1234";
        String expected = "1234";
        ByteArrayInputStream in = new ByteArrayInputStream(fullName.getBytes());
        instance.changeInputStream(in);
        try {
            instance.editName(doctor);
        } catch (Exception ex) {
        }

        assertEquals(expected, doctor.getFullName());
    }

    @Test
    public void editFullName_NormalCase_LenghtEqual50_ExpectSuccess() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        String input = "11111111111111111111111111111111111111111111111111";
        String expected = "11111111111111111111111111111111111111111111111111";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        instance.changeInputStream(in);

        try {
            instance.editName(doctor);
        } catch (Exception ex) {
        }

        assertEquals(expected, doctor.getFullName());
    }

    @Test
    public void editDateOfBirth_AbnormalCase_WrongFormat_ExpectError() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        String inputDate = "Date in dd/MM/yyyy format or your input date is not exist\n";
        String expected = "Enter Birth Of Date: Date in dd/MM/yyyy format or your input date is not exist";

        ByteArrayInputStream in = new ByteArrayInputStream(inputDate.getBytes());
        instance.changeInputStream(in);

        try {
            instance.editBirthOfDate(doctor);
        } catch (Exception ex) {
        }

        outStream.flush();
        String printedOutput = new String(outStream.toByteArray());
        String[] outputData = printedOutput.split(System.getProperty("line.separator"));
        assertEquals(expected, outputData[0]);
    }

    @Test
    public void editDateOfBirth_AbnormalCase_WrongFormat_ExpectError2() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        String inputDate = "22/2/200-";
        String expected = "Enter Birth Of Date: Date in dd/MM/yyyy format or your input date is not exist";

        ByteArrayInputStream in = new ByteArrayInputStream(inputDate.getBytes());
        instance.changeInputStream(in);

        try {
            instance.editBirthOfDate(doctor);
        } catch (Exception ex) {
        }

        outStream.flush();
        String printedOutput = new String(outStream.toByteArray());
        String[] outputData = printedOutput.split(System.getProperty("line.separator"));
        assertEquals(expected, outputData[0]);
    }

    @Test
    public void editDateOfBirth_AbnormalCase_EmptyInput_ExpectError() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        String inputDate = "\n";
        String expected = "Enter Birth Of Date: Date in dd/MM/yyyy format or your input date is not exist";

        ByteArrayInputStream in = new ByteArrayInputStream(inputDate.getBytes());
        instance.changeInputStream(in);

        try {
            instance.editBirthOfDate(doctor);
        } catch (Exception ex) {
        }

        outStream.flush();
        String printedOutput = new String(outStream.toByteArray());
        String[] outputData = printedOutput.split(System.getProperty("line.separator"));
        assertEquals(expected, outputData[0]);
    }

    @Test
    public void editDateOfBirth_AbnormalCase_TrailingSpace_ExpectError() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        String inputDate = "             \n";
        String expected = "Enter Birth Of Date: Date in dd/MM/yyyy format or your input date is not exist";

        ByteArrayInputStream in = new ByteArrayInputStream(inputDate.getBytes());
        instance.changeInputStream(in);

        try {
            instance.editBirthOfDate(doctor);
        } catch (Exception ex) {
        }

        outStream.flush();
        String printedOutput = new String(outStream.toByteArray());
        String[] outputData = printedOutput.split(System.getProperty("line.separator"));
        assertEquals(expected, outputData[0]);
    }

    @Test
    public void editDateOfBirth_AbnormalCase_WrongDate_ExpectError() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        String inputDate = "30/2/2022";
        String expected = "Enter Birth Of Date: Date in dd/MM/yyyy format or your input date is not exist";

        ByteArrayInputStream in = new ByteArrayInputStream(inputDate.getBytes());
        instance.changeInputStream(in);

        try {
            instance.editBirthOfDate(doctor);
        } catch (Exception ex) {
        }

        outStream.flush();
        String printedOutput = new String(outStream.toByteArray());
        String[] outputData = printedOutput.split(System.getProperty("line.separator"));
        assertEquals(expected, outputData[0]);
    }
    
    @Test
    public void editDateOfBirth_Normal_ExpectSuccess() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        String inputDate = "3/2/2022";
        String expected = "2022-02-03";

        ByteArrayInputStream in = new ByteArrayInputStream(inputDate.getBytes());
        instance.changeInputStream(in);

        try {
            instance.editBirthOfDate(doctor);
        } catch (Exception ex) {
        }

        outStream.flush();
        String printedOutput = new String(outStream.toByteArray());
        String[] outputData = printedOutput.split(System.getProperty("line.separator"));
        assertEquals(expected, doctor.getBirth().toString());
    }

    @Test
    public void editSpecialization_AbnormalCase_MoreThan255Character_ExpectError() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        String inputDate = "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
        String expected = "Enter Specialization: Text is not longer than 255";

        ByteArrayInputStream in = new ByteArrayInputStream(inputDate.getBytes());
        instance.changeInputStream(in);

        try {
            instance.editSpecialization(doctor);
        } catch (Exception ex) {
        }

        outStream.flush();
        String printedOutput = new String(outStream.toByteArray());
        String[] outputData = printedOutput.split(System.getProperty("line.separator"));
        assertEquals(expected, outputData[0]);
    }

    @Test
    public void editSpecialization_NormalCase_ExpectSuccess() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        String input = "abc";
        String expected = "abc";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        instance.changeInputStream(in);

        try {
            instance.editSpecialization(doctor);
        } catch (Exception ex) {
        }

//        outStream.flush();
//        String printedOutput = new String(outStream.toByteArray());
//        String[] outputData = printedOutput.split(System.getProperty("line.separator"));
        assertEquals(expected, doctor.getSpecialization());
    }

    @Test
    public void editSpecialization_NormalCase_TrailingSpace_ExpectSuccess() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        String input = "     ";
        String expected = "";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        instance.changeInputStream(in);

        try {
            instance.editSpecialization(doctor);
        } catch (Exception ex) {
        }

        assertEquals(expected, doctor.getSpecialization());
    }

    @Test
    public void editSpecialization_NormalCase_EmptyInput_ExpectSuccess() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        String input = "\n";
        String expected = "";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        instance.changeInputStream(in);

        try {
            instance.editSpecialization(doctor);
        } catch (Exception ex) {
        }

        assertEquals(expected, doctor.getSpecialization());
    }

    @Test
    public void editAvaiablity_NormalCase_EmptyInput_ExpectSuccess() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        String input = "\n";
        String expected = "Select: Your selection is not available";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        instance.changeInputStream(in);

        try {
            instance.editAvailability(doctor);
        } catch (Exception ex) {
        }

        outStream.flush();
        String printedOutput = new String(outStream.toByteArray());
        String[] outputData = printedOutput.split(System.getProperty("line.separator"));
        assertEquals("0: In Vacation", outputData[0]);
        assertEquals("1: Available", outputData[1]);
        assertEquals("2: Busy In Emergency", outputData[2]);
        assertEquals("3: Diagnose", outputData[3]);
        assertEquals(expected, outputData[4]);
    }

    @Test
    public void editAvaiablity_AbormalCase_NotAvailableSelection_ExpectSuccess() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        String input = "-1";
        String expected = "Select: Your selection is not available";

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        instance.changeInputStream(in);

        try {
            instance.editAvailability(doctor);
        } catch (Exception ex) {
        }

        outStream.flush();
        String printedOutput = new String(outStream.toByteArray());
        String[] outputData = printedOutput.split(System.getProperty("line.separator"));
        assertEquals("0: In Vacation", outputData[0]);
        assertEquals("1: Available", outputData[1]);
        assertEquals("2: Busy In Emergency", outputData[2]);
        assertEquals("3: Diagnose", outputData[3]);
        assertEquals(expected, outputData[4]);
    }

    @Test
    public void editAvaiablity_NormalCase_ExpectSuccess() throws IOException {
        DoctorManager instance = new DoctorManager();
        Doctor doctor = new Doctor();
        String input = "1";
        int expect = 1;
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        instance.changeInputStream(in);

        try {
            instance.editAvailability(doctor);
        } catch (Exception ex) {
        }

        assertEquals(expect, doctor.getAvailability());
    }

    @Test
    public void testAddDoctor() {
        System.out.println("addDoctor");
        DoctorManager instance = new DoctorManager();
        boolean expResult = true;
        boolean result = instance.addDoctor(d1);
        assertEquals(expResult, result);
    }
    
}
