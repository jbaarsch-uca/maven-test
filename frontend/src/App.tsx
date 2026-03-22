
import CourseList from './components/CourseList';

function App() {
    console.log("App is rendering!");
    return (
        <div className="App">
            <nav style={{ padding: '1rem', background: '#282c34', color: 'white' }}>
                <h1>Student Registration Portal</h1>
            </nav>
            <h1>Hello World, Again!</h1>
            <div>
                <CourseList />
            </div>
        </div>
    );
}

export default App;