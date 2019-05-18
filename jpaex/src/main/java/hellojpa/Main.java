package hellojpa;

import hellojpa.entity.Member;
import hellojpa.entity.MemberType;
import hellojpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try {
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setName("헬로");
            member.setMemberType(MemberType.ADMIN);
            member.setTeam(team);
            em.persist(member);

            // Fetch Join & Pagination
            String jpql = "select m from Member m join fetch m.team where m.name like '%헬%'";
            List<Member> members = em.createQuery(jpql, Member.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
            for (Member m: members) {
                // Fetch Join으로 회원과 팀을 함께 조회했으므로, 지연 로딩 발생 안함
                System.out.println("userName = " + m.getName() + ", " + "teamName = " + m.getTeam().getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
