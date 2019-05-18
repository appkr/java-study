## Jhipster Definition Language

#### JDL Basic
```jdl
entity JobHistory (job_histories) {
    startDate Instant,
    endDate Instant,
    language Language
}

enum Language {
    FRENCH, ENGLISH, SPANISH
}

relationship OneToOne {
    JobHistory{job} to Job,
    JobHistory{department} to Department,
    JobHistory{employee} to Employee
}
```
```java
@Entity
@Table(name = "job_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JobHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @OneToOne
    @JoinColumn(unique = true)
    private Job job;

    @OneToOne
    @JoinColumn(unique = true)
    private Department department;

    @OneToOne
    @JoinColumn(unique = true)
    private Employee employee;

}
```

#### JDL Relationships
```jdl
relationship OneToOne {
    // One way relationship
    Country{region} to Region
}
```
```java
public class Country {
    @OneToOne
    @JoinColumn(unique = true)
    private Region region;
}

public class Region {
    // No field
}
```
```jdl
relationship ManyToMany {
    Job{task(title)} to Task{job}
}
```
```java
public class Job {
    @ManyToMany
    @JoinTable(name = "job_task",
               joinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))
    private Set<Task> tasks = new HashSet<>();
}

public class Task {
    @ManyToMany(mappedBy = "tasks")
    private Set<Job> jobs = new HashSet<>();
}
```
```jdl
relationship OneToMany {
    Department{employee} to
    Employee
}
```
```java
public class Department {
    @OneToMany(mappedBy = "department")
    private Set<Employee> employees = new HashSet<>();
}

public class Employee {
    @ManyToOne
    private Department department;
}
```
```jdl
relationship ManyToOne {
    Employee{manager} to Employee
}
```
```java
public class Employee {
    @ManyToOne
    private Employee manager;
}
```

#### JDL Pagination
```
paginate JobHistory with infinite-scroll
paginate Job with pagination
```
```java
public class JobHistoryResource {
    @GetMapping("/job-histories")
    public ResponseEntity<List<JobHistoryDTO>> getAllJobHistories(Pageable pageable) {
        Page<JobHistoryDTO> page = jobHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/job-histories");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}

public interface JobHistoryService {
    JobHistoryDTO save(JobHistoryDTO jobHistoryDTO);
    Page<JobHistoryDTO> findAll(Pageable pageable);
}

// ---

public class JobResource {
    @GetMapping("/jobs")
    public ResponseEntity<List<JobDTO>> getAllJobs(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        Page<JobDTO> page;
        if (eagerload) {
            page = jobService.findAllWithEagerRelationships(pageable);
        } else {
            page = jobService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/jobs?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}

public interface JobService {
    Page<JobDTO> findAll(Pageable pageable);
    Page<JobDTO> findAllWithEagerRelationships(Pageable pageable);
}
```

#### JDL DTO & DTO-Entity Mappers & Services
- `serviceImpl`: 인터페이스와 구현 클래스 생성
- `serviceClass`: 인터페이스 없이 구현 클래스만 생성
- `noFluentMethod` 
```jdl
dto * with mapstruct
service all with serviceImpl
noFluentMethod {Entity}, {Entity}
```
```java
public class JobDTO implements Serializable {
    private Long id;
    private String jobTitle;
    private Long minSalary;
    private Long maxSalary;
    private Long employeeId;
    private Set<TaskDTO> tasks = new HashSet<>();
}

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, TaskMapper.class})
public interface JobMapper extends EntityMapper<JobDTO, Job> {

    @Mapping(source = "employee.id", target = "employeeId")
    JobDTO toDto(Job job);

    @Mapping(source = "employeeId", target = "employee")
    Job toEntity(JobDTO jobDTO);

}

@Service
@Transactional
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    @Override
    public JobDTO save(JobDTO jobDTO) {
        Job job = jobMapper.toEntity(jobDTO);
        job = jobRepository.save(job);
        return jobMapper.toDto(job);
    }

    @Override @Transactional(readOnly = true)
    public Page<JobDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Jobs");
        return jobRepository.findAll(pageable)
            .map(jobMapper::toDto);
    }

    @Override @Transactional(readOnly = true)
    public Optional<JobDTO> findOne(Long id) {
        log.debug("Request to get Job : {}", id);
        return jobRepository.findOneWithEagerRelationships(id)
            .map(jobMapper::toDto);
    }
}
```

#### To Enable Audit
- `AbstractAuditingEntity` 상속
- Custom `DateTimeMapper` 작성
- `@Mapper`에 `DateTimeMapper` 바인드
```java
@Entity
@Table(name = "foos")
public class Foo extends AbstractAuditingEntity implements Serializable {}
```
- Entity: `Instant`
- DTO: `OffsetDateTime`
```java
@Mapper(componentModel = "spring")
public interface DateTimeMapper {

    default Instant toInstant(OffsetDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toInstant();
    }

    default OffsetDateTime toOffsetDateTime(Instant dateTime) {
        if (dateTime == null) {
            return null;
        }
        return OffsetDateTime.ofInstant(dateTime, ZoneId.systemDefault());
    }

}
```
```java
@Mapper(componentModel = "spring", uses = {DateTimeMapper.class})
public interface FooMapper extends EntityMapper<FooDTO, Foo> {}
```
