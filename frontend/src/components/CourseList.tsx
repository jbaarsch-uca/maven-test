import React, { useEffect, useState } from 'react';
import { CourseResponse } from '../types/student-reg-types';
import { CourseService } from '../services/CourseService';

const CourseList: React.FC = () => {
    // State to store our courses
    const [courses, setCourses] = useState<CourseResponse[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [newCourseName, setNewCourseName] = useState("");
    const [newCourseInstructor, setNewCourseInstructor] = useState("");
    const [newCourseMaxSize, setNewCourseMaxSize] = useState("");
    const [newCourseRoom, setNewCourseRoom] = useState("");
    // The fetch data function.
    const loadCourses = async () => {
        try {
            const data = await CourseService.getAllCourses();
            setCourses(data);
        } catch (err) {
            setError(err instanceof Error ? err.message : 'An error occurred');
        } finally {
            setLoading(false);
        }
    };


    // Fetch data on component load
    useEffect(() => {
        loadCourses();
    }, []);

    const handleDelete = async (id: number) => {
        try {
            await CourseService.deleteCourse(id);
            // This triggers a re-render without a page refresh!
            setCourses(courses.filter(c => c.id !== id));
        } catch (err) {
            alert("Delete failed!");
        }
    };

    const handleAdd = async () => {
        // inputs always want to handle strings, so convert the max size to a number
        const numericSize = parseInt(newCourseMaxSize.trim());
        const numericInstructor = parseInt(newCourseInstructor.trim());
        // try to save the course.
        const savedCourse = await CourseService.addCourse(
            { name: newCourseName,
            instructor: numericInstructor,
            maxSize: numericSize,
            room: newCourseRoom,
            roster: undefined
            });
        console.log("I saved the courses--now, to update the fields.")
        // refresh the list of courses
        await loadCourses();
        //reset all the fields to clear the data

        setNewCourseName(""); // Clear the input
        setNewCourseInstructor("");
        setNewCourseMaxSize("");
        setNewCourseRoom("");

            };

    if (loading) return <div>Loading courses from Spring Boot...</div>;
    if (error) return <div style={{ color: 'red' }}>Error: {error}</div>;

    return (

        <div className="course-container">
            <input
                value={newCourseName}
                onChange={(e) => setNewCourseName(e.target.value)}
                placeholder="New Course Name"
            ></input>
            <input
                value={newCourseInstructor}
                onChange={(e) => setNewCourseInstructor(e.target.value)}
                placeholder = "Instructor Name"
            ></input>
            <input
                value ={newCourseMaxSize}
                onChange={(e) => setNewCourseMaxSize(e.target.value)}
                placeholder={"MaxSize"}
            ></input>
            <input
                value={newCourseRoom}
                onChange={(e) => setNewCourseRoom(e.target.value)}
                placeholder={"Room"}
            ></input>

            <button onClick={handleAdd}>Add Course</button>
            <h1>Available Courses</h1>
            <table>
                <thead>
                <tr>
                    <th>Course Name</th>
                    <th>Instructor</th>
                    <th>Room</th>
                    <th>Enrolled Count</th>
                    <th>Roster</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>
                {courses.map(course => (
                    <tr key={course.id}>
                        <td>{course.name}</td>
                        <td>{course.instructor}</td>
                        <td>{course.room}</td>
                        <td>{course.roster.length}</td>
                        <td>{course.roster.join(', ') || 'Empty'}</td>
                        <td>
                            <button onClick={() => handleDelete(course.id)} style={{color: 'red'}}>
                                Delete
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default CourseList;