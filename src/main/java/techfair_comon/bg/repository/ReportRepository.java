package techfair_comon.bg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import techfair_comon.entity.report.Report;
import techfair_comon.entity.report.ReportId;

public interface ReportRepository extends JpaRepository<Report, ReportId> {

    @Query("SELECT COUNT(1) FROM Report r WHERE r.bg.bgNo = :#{#paramReport.bg.bgNo}")
    int countReportByBgNo(@Param("paramReport") Report report);
}
