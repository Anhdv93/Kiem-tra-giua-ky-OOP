package th.system.domain.fcc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import th.system.domain.ScoreEntry;
import th.system.domain.Student;
import th.system.domain.constant.RegisterType;
import th.system.domain.register.FreeSubjectRegister;
import th.system.domain.register.ScheduledSubjectRegister;
import th.system.domain.register.SubjectRegister;

public class SubjectRegisterEntities {
    
    private Map<String, SubjectRegister> registerMap;
    
    public SubjectRegisterEntities() {
        registerMap = new HashMap<>();
    }
    
    public boolean doesStudentRegister(String key) {
        return registerMap.containsKey(key);
    }
    
    public void registerStudent(Student student, RegisterType registerType) {
        registerMap.put(student.getId(), createSubjectRegister(student, registerType));
    }
    
    private SubjectRegister createSubjectRegister(Student student, RegisterType registerType) {
        return registerType == RegisterType.SCHEDULED ? new ScheduledSubjectRegister(student) : new FreeSubjectRegister(student);
    }
    
    public List<SubjectRegister> getAll() {
        return new ArrayList<>(registerMap.values());
    }
    
    public ScoreEntries getScoreEntries(String studentId) {
        return registerMap.get(studentId).getScoreEntries();
    }
    
    public ScoreEntries getAllScoredSubject(String studentId) {
        return new ScoreEntries(
            getScoreEntries(studentId).getAll().stream().filter(e -> e.getMidtermScore().isPresent()).collect(Collectors.toList()));
    }
    
    public ScoreEntries getAllNotScoredSubject(String studentId) {
        return new ScoreEntries(
            getScoreEntries(studentId).getAll().stream().filter(e -> !e.getMidtermScore().isPresent()).collect(Collectors.toList()));
    }
    
    public void registerSubject(String studentId, ScoreEntry entry) {
        registerMap.get(studentId).registerSubject(entry);
    }
    
    public void updateScore(String studentId, ScoreEntry entry) {
        registerMap.get(studentId).getScoreEntries().addEntry(entry);
    }
    
    public void updateScore(String studentId, String subjectId, float midtermScore, float finalScore) {
        ScoreEntry subjectEntry = registerMap.get(studentId).getScoreEntries().findEntryById(subjectId);
        if(subjectEntry != null) {
            subjectEntry.updateScore(midtermScore, finalScore);
        }
    }
    
    public boolean isEmpty() {
        return registerMap.isEmpty();
    }
}
