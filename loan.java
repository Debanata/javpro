@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private Double interestRate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    // getters and setters
}
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long userId);
}
@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    public List<Loan> findByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    public Loan save(Loan loan) {
        return loanRepository.save(loan);
    }
}
@RestController
@RequestMapping("/api/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Loan>> getLoansByUserId(@PathVariable Long userId) {
        List<Loan> loans = loanService.findByUserId(userId);
        return ResponseEntity.ok(loans);
    }

    @PostMapping
    public ResponseEntity<Loan> applyForLoan(@RequestBody Loan loan) {
        Loan savedLoan = loanService.save(loan);
        return ResponseEntity.ok(savedLoan);
    }
}
