package org.zgl.db.dao.mapper;
import org.zgl.db.dao.entity.Task;
import java.util.List;
/**
 * @author： 猪哥亮
 * @创建时间： 2018/8/13
 * @文件描述：
 */
public interface TaskMapper {
    List<Task> queryTaskUid(Long id);
    int updateTask(Task sigin);
    int insertTask(Task sigin);
    int deleteTask(Integer id);
}
