package com.goom.springapi2.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder // builder를 사용할 수 있게함
@Entity  // jpa entity임을 알림
@Getter //user 필드값의 getter를 자동으로 생성함
@NoArgsConstructor //인자 없는 생성자를 자동으로 생성
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성
@Table(name="user") //'user' 테이블과 매핑됨을 명시

public class User implements UserDetails {
    @Id// primaryKey 임을 알림
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long msrl; // pk 생성 전략을 DB에 위함함을 의미.(mysql로 보면 pk 필드를 auto_increment로 설정해 놓은 경우로 보면 됨)

    @Column(nullable = false, unique = true, length = 30) //uid column을 명시함, 필수이고 유니크한 필드이며 길이는 30
    private String uid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;


    /**
     *  roles는 회원이 가지고 있는 권한 정보, 
     *  가입 했을 때 기본 "ROLE_USER"가 세팅됨.
     *  권한은 회원당 여러 개가 세팅될 수 있으므로 Collection으로 선언
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    /**
     *  security에서 사용하는 회원 구분 id(여기서는 uid 로 변경)
     * @return
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.uid;
    }

    /**
     *  다음 값들은 Security에서 사용하는 회원 상태 값( 여기선 모두 사용하 안하므로 true로 설정)
     *  Json 결과로 출력 안할 데이터는 @JsonProperty(access = JSonProperty.Access.WRITE_ONLY) annotation 설정
     * @return
     */
    
    // 계정 만료 여부
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠김 여부
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 패스워드 만료 여부
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용 가능 여부
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
