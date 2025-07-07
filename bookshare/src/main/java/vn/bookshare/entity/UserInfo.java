package vn.bookshare.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vn.bookshare.common.base.Auditable;

@Entity
@Table(name = "user_info")
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo extends Auditable implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "username", length = 255)
    @Getter
    private String username;
    @Basic(optional = false)
    @Column(name = "ordinal_number", nullable = false, unique = true)
    @Getter
    private Long ordinalNumber;
    @Basic(optional = false)
    @Column(name = "fullname", length = 255, nullable = false)
    @Getter
    @Setter
    private String fullname;
    @Basic(optional = false)
    @Column(name = "date_of_birth", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Getter
    @Setter
    private LocalDate dateOfBirth;
    @Basic(optional = false)
    @Column(name = "gender", length = 100, nullable = false)
    @Getter
    @Setter
    private String gender;
    @Basic(optional = false)
    @Column(name = "nationality", length = 255, nullable = false)
    @Getter
    @Setter
    private String nationality;
    @Basic(optional = false)
    @Column(name = "phone", length = 100, nullable = false)
    @Getter
    @Setter
    private String phone;
    @Basic(optional = false)
    @Column(name = "note", nullable = true, columnDefinition = "TEXT")
    @Getter
    @Setter
    private String note;
    @OneToOne
    @MapsId
    @JoinColumn(name = "username")
    @Getter
    @Setter
    private AccountUser accountUser;
}
