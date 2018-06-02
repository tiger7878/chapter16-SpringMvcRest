package spittr.data;

import spittr.domain.Spittle;

import java.util.List;

/**
 * User: monkey
 * Date: 2018-06-01 15:03
 */
public interface SpittleRepository {

    List<Spittle> findRecentSpittles();

    List<Spittle> findSpittles(long max, int count);

    Spittle findOne(long id);

    Spittle save(Spittle spittle);

}
