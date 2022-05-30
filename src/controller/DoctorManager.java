package controller;

import java.io.InputStream;
import model.Doctor;
import util.InputReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class DoctorManager {

    private InputReader inputReader;
    private final List<Doctor> doctors;

    public DoctorManager() {
        doctors = new ArrayList<>();
        inputReader = new InputReader(System.in);
    }

    public void changeInputStream(InputStream in) {
        inputReader = new InputReader(in);
    }
    
    @Deprecated
    public boolean containDoctor(Doctor doctor) {
        return doctors.stream().anyMatch(d -> d.getID() == doctor.getID());
    }

    public boolean addDoctor(Doctor doctor) {
        if (doctors.contains(doctor)) {
            return false;
        }
        return doctors.add(doctor);
    }

    public boolean removeDoctor(long Id) {
        return doctors.removeIf(d -> d.getID() == Id);
    }

    public List<Doctor> findByName(String name) {
        List<Doctor> ls = new ArrayList<>();
        for (Doctor d : doctors) {
            if (d.getFullName().toLowerCase().contains(name.toLowerCase())) {
                ls.add(d);
            }
        }

        return ls;
    }

    public Doctor findByID(long ID) {
        return doctors
                .stream()
                .filter(d -> d.getID() == ID)
                .findFirst()
                .orElse(null);
    }

    public List<Doctor> sortByDateOfBirth() {
        List<Doctor> sorted = new ArrayList<>(doctors);
        sorted.sort(Comparator.comparing(Doctor::getBirth));
        return sorted;
    }

    public void createDoctor() {
        Doctor doctor = new Doctor();
        editName(doctor);
        editBirthOfDate(doctor);
        editSpecialization(doctor);
        editAvailability(doctor);
        editEmail(doctor);
        editPhoneNumber(doctor);
        if (addDoctor(doctor)) {
            System.out.println("Add " + doctor.getFullName() + " successfully!");
        } else {
            System.out.println("Add doctor fail!");
        }
    }

    private void printListDoctor(List<Doctor> doctors) {
        String format = "| %3s | %50s | %10s | %20s | %15s | %30s | %20s |\n";
        System.out.format(format,
                "ID",
                "Full Name",
                "DOB",
                "Specialization",
                "Availability",
                "Email",
                "Phone"
        );
        if (doctors.isEmpty()) {
            System.out.println("Empty");
            return;
        }
        for (Doctor d : doctors) {
            printDoctor(d);
        }
    }

    private void printDoctor(Doctor d) {
        String format = "| %3s | %50s | %10s | %20s | %15s | %30s | %20s |\n";
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedSpecialization = (d.getSpecialization().length() > 20)
                ? (d.getSpecialization().substring(0, 18) + "...")
                : d.getSpecialization();

        System.out.format(format,
                d.getID(),
                d.getFullName(),
                d.getBirth().format(dft),
                formattedSpecialization,
                d.getAvailabilityName(),
                d.getEmail(),
                d.getPhoneNumber()
        );
    }

    public void printMenu() {
        System.out.println("0. List all doctor");
        System.out.println("1. Create a new doctor");
        System.out.println("2. Edit doctor's information");
        System.out.println("3. Find by ID");
        System.out.println("4. Find by name");
        System.out.println("5. Delete by ID");
        System.out.println("6. Sort by date of birth");
        System.out.println("7. Exit");
    }

    private void printEditMenu() {
        System.out.println("==== Edit Doctor Information ====");
        System.out.println("Choose information you want to edit.");
        System.out.println("2. Edit Name");
        System.out.println("3. Edit Date Of Birth");
        System.out.println("4. Edit Specialization");
        System.out.println("5. Edit Availability");
        System.out.println("6. Edit Email");
        System.out.println("7. Edit Phone Number");
        System.out.println("0. Cancel");
    }

    public void editName(Doctor d) {
        String fullName = inputReader.getString("Enter Full Name: ",
                "Name must be not empty and length <= 50",
                s -> (!s.isEmpty() && s.length() <= 50)
        );
        d.setFullName(fullName);
    }

    public void editBirthOfDate(Doctor d) {
        LocalDate birthOfDate = inputReader.getDate("Enter Birth Of Date: ",
                "Date in dd/MM/yyyy format or your input date is not exist"
        );
        d.setBirth(birthOfDate);
    }

    public void editSpecialization(Doctor d) {
        String specialization = inputReader.getString("Enter Specialization: ",
                "Text is not longer than 255",
                s -> s.length() <= 255
        );
        d.setSpecialization(specialization);
    }

    public void editAvailability(Doctor d) {
        System.out.println("0: In Vacation");
        System.out.println("1: Available");
        System.out.println("2: Busy In Emergency");
        System.out.println("3: Diagnose");
        int availability = inputReader.getInteger("Select: ",
                "Your selection is not available",
                n -> (n >= 0 && n < 4)
        );
        d.setAvailability(availability);
    }

    public void editEmail(Doctor d) {
        String email = inputReader.getString("Enter Email(abcxyz.@email): ",
                "Email is not valid format",
                s -> s.matches(InputReader.EMAIL_REGEX)
        );
        d.setEmail(email);
    }

    public void editPhoneNumber(Doctor d) {
        String phoneNumber = inputReader.getString("Enter Phone Number: ",
                "Phone number is not valid! Must be in (000)-000-0000 format",
                s -> s.matches(InputReader.PHONE_NUMBER_FORMAT)
        );
        d.setPhoneNumber(phoneNumber);
    }

    private void editDoctor() {
        do {
            int id = inputReader.getInteger("Enter ID: ", "Invalid ID", n -> true);
            Doctor doctor = findByID(id);
            if (doctor == null) {
                String choice = inputReader.getString("Search again ?\n"
                        + "[Y] Yes  [N] No (default is \"Y\"): ",
                        "", s -> s.matches("[YyNn]")
                );
                if (choice.equalsIgnoreCase("n")) {
                    return;
                }
            } else {
                printDoctorInForm(doctor);
                int choice = inputReader.getInteger("Select: ", "", n -> (n >= 0 && n < 8));
                printEditMenu();
                switch (choice) {
                    case 0:
                        return;
                    case 1:
                        break;
                    case 2:
                        editName(doctor);
                        break;
                    case 3:
                        editBirthOfDate(doctor);
                        break;
                    case 4:
                        editSpecialization(doctor);
                        break;
                    case 5:
                        editAvailability(doctor);
                        break;
                    case 6:
                        editEmail(doctor);
                        break;
                    case 7:
                        editPhoneNumber(doctor);
                        break;
                }
            }
        } while (true);
    }

    public void run() {
        while (true) {
            printMenu();
            int selection = inputReader.getInteger("Select: ",
                    "Invalid selection!",
                    n -> (n >= 0 && n < 7)
            );

            switch (selection) {
                case 0:
                    printListDoctor(doctors);
                    break;
                case 1:
                    createDoctor();
                    break;
                case 2:
                    editDoctor();
                    break;
                case 3:
                    System.out.println("Find By ID");
                    int id = inputReader.getInteger("Enter ID: ", "", n -> true);
                    printDoctorInForm(findByID(id));
                    break;
                case 4:
                    System.out.println("Find By Name");
                    String name = inputReader.getString("Enter Name: ");
                    printListDoctor(findByName(name));
                    break;
                case 5:
                    deleteDoctor();
                    break;
                case 6:
                    printListDoctor(sortByDateOfBirth());
                    break;
                case 7:
                    return;
            }
        }
    }

    private void printDoctorInForm(Doctor d) {
        System.out.println("Doctor Information");
        System.out.println("ID: " + d.getID());
        System.out.println("Full Name: " + d.getFullName());
        System.out.println("Date Of Birth: "
                + d.getBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
        System.out.println("Specialization: " + d.getSpecialization());
        System.out.println("Availability: " + d.getAvailabilityName());
        System.out.println("Email: " + d.getEmail());
        System.out.println("Phone: " + d.getPhoneNumber());
    }

    public void deleteDoctor() {
        System.out.println("==== Delete Doctor ====");
        long id = inputReader.getInteger("Enter ID (-1 to cancel): ",
                "Input is not valid",
                n -> n >= -1
        );

        if (id == -1) {
            return;
        }

        Doctor doctor = findByID(id);

        if (doctor == null) {
            System.out.println("Doctor with ID " + id + " is not existed");
            return;
        }

        printDoctorInForm(doctor);

        String choice = inputReader.getString("Confirm Delete(Y/N): ",
                "Invalid!",
                s -> s.equalsIgnoreCase("N") || s.equalsIgnoreCase("Y")
        );

        if (choice.equalsIgnoreCase("Y")
                && removeDoctor(doctor.getID())) {
            System.out.println("Delete doctor successfully");
        }
    }

    public static void main(String[] args) {
        new DoctorManager().run();
    }
}
