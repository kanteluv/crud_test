package com.sparta.crudtest.repository;

import com.sparta.crudtest.entity.MemberComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface MemberCommentRepository extends JpaRepository<MemberComment, Long> {

    List<MemberComment> findAll();

//    String getByTestId =
//            " SELECT new map(a.test_id as test_id, a.test_nm as test) " +
//                    "   FROM Test a WHERE a.test_id = :test_id ";
//    @Query(value = getByTestId)
//    List<Map<String, Object>> getByTestId(@Param("test_id") String test_id);

//    @Query("SELECT distinct new map (  i.svcCd as svcCd, i.svcNm as svcNm ) FROM BlockchainCd i WHERE i.state = true")
//    public List<Map<String, Object>> findDistinctSvcByStateTrue();
//
//    @Query("SELECT new map ( i.docCd as docCd , i.docNm as docNm, i.etc as etc ) FROM BlockchainCd i WHERE i.svcCd= :svcCd AND i.state = true")
//    public List<Map<String, Object>> findDocsBySvcCdAndStateTrue(@Param("svcCd") String svcCd);
}
