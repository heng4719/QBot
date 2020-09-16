package com.whitemagic2014.dao;

import com.whitemagic2014.pojo.MemberSan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberSanDao {
    MemberSan findMemberSan(@Param("uid") String uid);
    int insertMemberSan(MemberSan memberSan);
    int updateMemberSan(MemberSan memberSan);
    int deleteMemberSan(@Param("id") Integer id);
}
