package com.goom.springapi2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Builder // builder를 사용할 수 있게함
@Entity  // jpa entity임을 알림
@Getter //user 필드값의 getter를 자동으로 생성함
@NoArgsConstructor //인자 없는 생성자를 자동으로 생성
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성
@Table(name="user") //'user' 테이블과 매핑됨을 명시

public class User {
    @Id// primaryKey 임을 알림
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // pk 생성 전략을 DB에 위함함을 의미.(mysql로 보면 pk 필드를 auto_increment로 설정해 놓은 경우로 보면 됨)
    private long msrl;
    @Column(nullable = false, unique = true, length = 30) //uid column을 명시함, 필수이고 유니크한 필드이며 길이는 30
    private String uid;

    @Column(nullable = false, length = 100)
    private String name;
}
