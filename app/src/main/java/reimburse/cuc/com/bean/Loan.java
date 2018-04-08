package reimburse.cuc.com.bean;

/**
 * Created by hp1 on 2018/3/29.
 */
public class Loan {

    private Integer loan_uuid;
    private String loan_cause;
    private String loan_amount;
    private String loan_project_name;
    private String loan_user_name;
    private String loan_user_bank_number;
    private String loan_user_bank_name;
    private String loan_status;
    private Integer loan_check_account_id;
    private String loan_check_account_name;
    private String loan_check_comments;
    private String loan_commit_time;
    private String loan_check_complete_time;
    private Integer loan_project_uuid;
    private Integer loan_commit_user_id;




    public Loan(){

    }



    public Loan(String loan_cause, String loan_amount,
                String loan_project_name, String loan_user_name,
                String loan_user_bank_number, String loan_user_bank_name,
                Integer loan_project_uuid, Integer loan_commit_user_id) {
        this.loan_cause = loan_cause;
        this.loan_amount = loan_amount;
        this.loan_project_name = loan_project_name;
        this.loan_user_name = loan_user_name;
        this.loan_user_bank_number = loan_user_bank_number;
        this.loan_user_bank_name = loan_user_bank_name;
        this.loan_project_uuid = loan_project_uuid;
        this.loan_commit_user_id = loan_commit_user_id;
    }



    public Integer getLoan_uuid() {
        return loan_uuid;
    }

    public void setLoan_uuid(Integer loan_uuid) {
        this.loan_uuid = loan_uuid;
    }

    public String getLoan_cause() {
        return loan_cause;
    }

    public void setLoan_cause(String loan_cause) {
        this.loan_cause = loan_cause;
    }

    public String getLoan_amount() {
        return loan_amount;
    }

    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }

    public String getLoan_project_name() {
        return loan_project_name;
    }

    public void setLoan_project_name(String loan_project_name) {
        this.loan_project_name = loan_project_name;
    }

    public String getLoan_user_name() {
        return loan_user_name;
    }

    public void setLoan_user_name(String loan_user_name) {
        this.loan_user_name = loan_user_name;
    }

    public String getLoan_user_bank_number() {
        return loan_user_bank_number;
    }

    public void setLoan_user_bank_number(String loan_user_bank_number) {
        this.loan_user_bank_number = loan_user_bank_number;
    }

    public String getLoan_user_bank_name() {
        return loan_user_bank_name;
    }

    public void setLoan_user_bank_name(String loan_user_bank_name) {
        this.loan_user_bank_name = loan_user_bank_name;
    }

    public String getLoan_status() {
        return loan_status;
    }

    public void setLoan_status(String loan_status) {
        this.loan_status = loan_status;
    }

    public Integer getLoan_check_account_id() {
        return loan_check_account_id;
    }

    public void setLoan_check_account_id(Integer loan_check_account_id) {
        this.loan_check_account_id = loan_check_account_id;
    }

    public String getLoan_check_account_name() {
        return loan_check_account_name;
    }

    public void setLoan_check_account_name(String loan_check_account_name) {
        this.loan_check_account_name = loan_check_account_name;
    }

    public String getLoan_check_comments() {
        return loan_check_comments;
    }

    public void setLoan_check_comments(String loan_check_comments) {
        this.loan_check_comments = loan_check_comments;
    }

    public String getLoan_commit_time() {
        return loan_commit_time;
    }

    public void setLoan_commit_time(String loan_commit_time) {
        this.loan_commit_time = loan_commit_time;
    }

    public String getLoan_check_complete_time() {
        return loan_check_complete_time;
    }

    public void setLoan_check_complete_time(String loan_check_complete_time) {
        this.loan_check_complete_time = loan_check_complete_time;
    }


    public Integer getLoan_project_uuid() {
        return loan_project_uuid;
    }


    public void setLoan_project_uuid(Integer loan_project_uuid) {
        this.loan_project_uuid = loan_project_uuid;
    }

    public Integer getLoan_commit_user_id() {
        return loan_commit_user_id;
    }

    public void setLoan_commit_user_id(Integer loan_commit_user_id) {
        this.loan_commit_user_id = loan_commit_user_id;
    }
}
