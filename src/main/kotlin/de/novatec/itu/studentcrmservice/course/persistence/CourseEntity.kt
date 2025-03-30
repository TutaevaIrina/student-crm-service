package de.novatec.itu.studentcrmservice.course.persistence

import de.novatec.itu.studentcrmservice.course.business.model.CourseDTO
import de.novatec.itu.studentcrmservice.course.business.model.CourseSimpleDTO
import de.novatec.itu.studentcrmservice.student.persistence.StudentEntity
import jakarta.persistence.*
import jakarta.validation.constraints.Size

@Entity
@Table(name = "courses")
class CourseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq_gen")
    @SequenceGenerator(name = "course_seq_gen", sequenceName = "seq_courses_id", allocationSize = 1)
    @Column(name = "id")
    val id: Long,

    @Column(name = "course_name")
    @Size(max = 40)
    var courseName: String,

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val students: MutableSet<StudentEntity> = mutableSetOf()
    ) {
    fun toDto(): CourseDTO {
        return CourseDTO(
            id = this.id,
            courseName = this.courseName,
            students = this.students.map { it.id }.toMutableSet()
        )
    }

    fun toSimpleDTO(): CourseSimpleDTO {
        return CourseSimpleDTO(
            id = this.id,
            courseName = this.courseName
        )
    }
}
