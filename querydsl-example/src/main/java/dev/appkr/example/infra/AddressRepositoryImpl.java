package dev.appkr.example.infra;

import static dev.appkr.example.domain.QAddress.address;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.appkr.example.api.dto.AddressSearchParam;
import dev.appkr.example.api.dto.RegionSearchParam;
import dev.appkr.example.domain.Address;
import dev.appkr.example.api.dto.AdminDto;
import dev.appkr.example.api.dto.LegalDto;
import dev.appkr.example.api.dto.RegionDto;
import dev.appkr.example.api.dto.RoadDto;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;

@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepositoryCustom {

  private final JPAQueryFactory factory;
  private final EntityManager em;

  @Override
  public Page<Address> findAllAddressBy(AddressSearchParam searchParam, Pageable pageable) {
    JPAQuery<Address> query = factory.selectFrom(address);
    applySearchParam(query, searchParam);
    JPQLQuery<Address> jpqlQuery = applyPageable(query, pageable);

    final long total = jpqlQuery.fetchCount();
    final List<Address> content = jpqlQuery.fetch();

    return new PageImpl<Address>(content, pageable, total);
  }

  @Override
  public Page<? extends RegionDto> findAllRegionBy(RegionSearchParam searchParam, Pageable pageable) {
    JPAQuery<String> cQuery = null;
    JPAQuery<? extends RegionDto> query = null;
    switch (searchParam.getRegionType()) {
      case ROAD:
        cQuery = factory.selectDistinct(address.roadCode).from(address);
        query = factory
            .from(address)
            .select(Projections.bean(RoadDto.class, address.sido, address.sigungu, address.emd, address.roadName, address.roadCode))
            .groupBy(address.sido, address.sigungu, address.emd, address.roadName, address.roadCode)
            .offset(pageable.getPageNumber() * pageable.getPageSize())
            .limit(pageable.getPageSize());
        break;
      case LEGAL:
        cQuery = factory.selectDistinct(address.legalCode).from(address);
        query = factory
            .from(address)
            .select(Projections.bean(LegalDto.class, address.sido, address.sigungu, address.legalDong, address.ri, address.legalCode))
            .groupBy(address.sido, address.sigungu, address.legalDong, address.ri, address.legalCode)
            .offset(pageable.getPageNumber() * pageable.getPageSize())
            .limit(pageable.getPageSize());
        break;
      case ADMIN:
        cQuery = factory.selectDistinct(address.adminCode).from(address);
        query = factory
            .from(address)
            .select(Projections.bean(AdminDto.class, address.sido, address.sigungu, address.adminDong, address.adminCode))
            .groupBy(address.sido, address.sido, address.sigungu, address.adminDong, address.adminCode)
            .offset(pageable.getPageNumber() * pageable.getPageSize())
            .limit(pageable.getPageSize());
        break;
    }

    applySearchParam(cQuery, query, searchParam);

    return new PageImpl(query.fetch(), pageable, cQuery.fetchCount());
  }

  private void applySearchParam(JPAQuery countQuery, JPAQuery query, RegionSearchParam searchParam) {
    BooleanBuilder builder = new BooleanBuilder();

    String depth1 = searchParam.getDepth1();
    if (StringUtils.isNotEmpty(depth1)) {
      builder.and(address.sido.eq(depth1));
    }

    String depth2 = searchParam.getDepth2();
    if (StringUtils.isNotEmpty(depth2)) {
      builder.and(address.sigungu.eq(depth2));
    }

    countQuery.where(builder);
    query.where(builder);
  }

  private void applySearchParam(JPAQuery<Address> query, AddressSearchParam searchParam) {
    BooleanBuilder builder = new BooleanBuilder();

    final String depth1 = searchParam.getDepth1();
    if (StringUtils.isNotEmpty(depth1)) {
      builder.and(address.sido.eq(depth1));
    }

    final String depth2 = searchParam.getDepth2();
    if (StringUtils.isNotEmpty(depth2)) {
      builder.and(address.sigungu.eq(depth2));
    }

    final String depth3 = searchParam.getDepth3();
    final String depth4 = searchParam.getDepth4();
    final String number = searchParam.getNumber();
    switch (searchParam.getRegionType()) {
      case ROAD:
        if (StringUtils.isNotEmpty(depth3)) {
          builder.and(address.emd.eq(depth3));
        }

        if (StringUtils.isNotEmpty(depth4)) {
          builder.and(address.roadName.eq(depth4));
        }

        if (StringUtils.isNotEmpty(number)) {
          builder.and(address.buildingNo.eq(number));
        }
        break;

      case LEGAL:
        if (StringUtils.isNotEmpty(depth3)) {
          builder.and(address.legalDong.eq(depth3));
        }

        if (StringUtils.isNotEmpty(depth4)) {
          builder.and(address.ri.eq(depth4));
        }

        if (StringUtils.isNotEmpty(number)) {
          builder.and(address.beonji.eq(number));
        }
        break;

      case ADMIN:
        if (StringUtils.isNotEmpty(depth3)) {
          builder.and(address.adminDong.eq(depth3));
        }

        if (StringUtils.isNotEmpty(number)) {
          builder.and(address.beonji.eq(number));
        }
        break;
    }

    query.where(builder);
  }

  private JPQLQuery<Address> applyPageable(JPAQuery<Address> query, Pageable pageable) {
    PathBuilder<Address> builder = new PathBuilderFactory().create(Address.class);
    return new Querydsl(em, builder).applyPagination(pageable, query);
  }
}
