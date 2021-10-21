package seedu.duke.module;

import seedu.duke.exceptions.module.IllegalModuleIndexException;
import seedu.duke.parser.module.ParserModule;
import seedu.duke.storage.StorageModule;

import java.io.IOException;

public class ModuleManager {
    static GradePoints gradePoints = new GradePoints();
    static int totalModularCredits = 0;
    static StorageModule storageModule = new StorageModule();
    static ParserModule parserModule = new ParserModule();

    public ModuleManager() {

    }

    public void addNewModule(Module module) throws IOException {
        ModuleList moduleList = storageModule.readDataFromFile();
        moduleList.addModule(module);
        storageModule.saveDataToFile(moduleList);
    }

    public void deleteModule(int moduleIndex) throws IOException, IllegalModuleIndexException {
        ModuleList moduleList = storageModule.readDataFromFile();
        System.out.println("read");
        boolean isValidIndex = (moduleIndex >= 0) && (moduleIndex < moduleList.getNumberOfModules());
        if (!isValidIndex) {
            throw new IllegalModuleIndexException();
        }
        System.out.println("I have deleted this module:");
        System.out.println(moduleList.getModuleByIndex(moduleIndex));
        moduleList.removeModuleByIndex(moduleIndex);
        storageModule.saveDataToFile(moduleList);
    }

    public double getExpectedCap() throws IOException {
        double totalPoints = 0.0;
        totalModularCredits = 0;
        ModuleList moduleList = storageModule.readDataFromFile();
        for (Module module : moduleList.getModules()) {
            double point = gradePoints.getPoint(module.getExpectedGrade());
            if (point != -1) {
                int credits = module.getModularCredits();
                totalPoints += point * credits;
                totalModularCredits += credits;
            }
        }
        return totalPoints / totalModularCredits;
    }

}
