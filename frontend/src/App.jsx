import { useState } from 'react'
import {
  ArrowDownUp,
  ArrowUpRight,
  Bell,
  Check,
  CheckCircle2,
  ClipboardList,
  FolderKanban,
  LayoutDashboard,
  List,
  Plus,
  Search,
  Users,
  X,
} from 'lucide-react'
import './App.css'

const initialProjects = [
  {
    id: 1,
    name: 'Plataforma de operaciones',
    objective: 'Centralizar solicitudes y automatizar el seguimiento interno.',
    owner: 'Lucía Méndez',
    progress: 72,
    due: '18 oct 2026',
    color: 'green',
    initials: ['LM', 'CR', 'AV'],
  },
  {
    id: 2,
    name: 'Portal de clientes',
    objective: 'Mejorar el autoservicio y la comunicación con clientes.',
    owner: 'Tomás Rivas',
    progress: 46,
    due: '04 nov 2026',
    color: 'coral',
    initials: ['TR', 'MP', 'SG'],
  },
  {
    id: 3,
    name: 'Migración de datos',
    objective: 'Unificar información histórica en una plataforma segura.',
    owner: 'Camila Ruiz',
    progress: 28,
    due: '21 nov 2026',
    color: 'blue',
    initials: ['CR', 'LM'],
  },
]

const initialTasks = [
  { id: 1, title: 'Definir el flujo de aprobación', projectId: 1, assignee: 'Camila Ruiz', initials: 'CR', due: 'Hoy', priority: 'Alta', status: 'En progreso' },
  { id: 2, title: 'Revisar permisos por equipo', projectId: 1, assignee: 'Andrés Vega', initials: 'AV', due: '30 sep', priority: 'Media', status: 'Pendiente' },
  { id: 3, title: 'Preparar prototipo de navegación', projectId: 2, assignee: 'Marina Paz', initials: 'MP', due: '01 oct', priority: 'Alta', status: 'En progreso' },
  { id: 4, title: 'Validar textos de ayuda', projectId: 2, assignee: 'Sofía Gil', initials: 'SG', due: '03 oct', priority: 'Baja', status: 'Pendiente' },
  { id: 5, title: 'Inventariar fuentes de datos', projectId: 3, assignee: 'Lucía Méndez', initials: 'LM', due: '02 oct', priority: 'Media', status: 'Completada' },
  { id: 6, title: 'Acordar estructura de reportes', projectId: 1, assignee: 'Tomás Rivas', initials: 'TR', due: '06 oct', priority: 'Baja', status: 'Pendiente' },
  { id: 7, title: 'Documentar plan de importación', projectId: 3, assignee: 'Camila Ruiz', initials: 'CR', due: '08 oct', priority: 'Alta', status: 'En progreso' },
]

const statusOptions = ['Todas', 'Pendiente', 'En progreso', 'Completada']
const columns = ['Pendiente', 'En progreso', 'Completada']

function App() {
  const [projects] = useState(initialProjects)
  const [tasks, setTasks] = useState(initialTasks)
  const [activeProject, setActiveProject] = useState(null)
  const [activeStatus, setActiveStatus] = useState('Todas')
  const [search, setSearch] = useState('')
  const [view, setView] = useState('list')
  const [isTaskFormOpen, setIsTaskFormOpen] = useState(false)
  const [activeNav, setActiveNav] = useState('Resumen')

  const visibleTasks = tasks.filter((task) => {
    const matchesProject = activeProject === null || task.projectId === activeProject
    const matchesStatus = activeStatus === 'Todas' || task.status === activeStatus
    const matchesSearch = `${task.title} ${task.assignee}`.toLowerCase().includes(search.toLowerCase())
    return matchesProject && matchesStatus && matchesSearch
  })

  const getProjectName = (projectId) => projects.find((project) => project.id === projectId)?.name ?? 'Sin proyecto'

  function updateTaskStatus(taskId, status) {
    setTasks((currentTasks) => currentTasks.map((task) => task.id === taskId ? { ...task, status } : task))
  }

  function createTask(event) {
    event.preventDefault()
    const formData = new FormData(event.currentTarget)
    const assignee = formData.get('assignee')
    setTasks((currentTasks) => [
      {
        id: Date.now(),
        title: formData.get('title').trim(),
        projectId: Number(formData.get('projectId')),
        assignee,
        initials: assignee.split(' ').map((part) => part[0]).join('').slice(0, 2).toUpperCase(),
        due: formData.get('due') || 'Sin fecha',
        priority: formData.get('priority'),
        status: 'Pendiente',
      },
      ...currentTasks,
    ])
    setActiveStatus('Todas')
    setActiveProject(null)
    setIsTaskFormOpen(false)
  }

  function navigateTo(section) {
    setActiveNav(section)
    document.getElementById(section === 'Proyectos' ? 'projects' : section === 'Tareas' ? 'tasks' : 'top')?.scrollIntoView({ behavior: 'smooth' })
  }

  return (
    <div className="app-shell" id="top">
      <aside className="sidebar">
        <a className="brand" href="#top" aria-label="Norte, inicio">
          <span className="brand-mark"><Check size={19} strokeWidth={3} /></span>
          <span>Norte<span className="brand-period">.</span></span>
        </a>

        <div className="workspace-label">ESPACIO DE TRABAJO</div>
        <button className="workspace-switch" type="button">
          <span className="workspace-avatar">E</span>
          <span className="workspace-copy"><strong>Estudio Norte</strong><small>Plan de equipo</small></span>
          <ArrowDownUp size={14} />
        </button>

        <nav className="primary-nav" aria-label="Navegación principal">
          <span className="nav-caption">GESTIÓN</span>
          <button className={`nav-item ${activeNav === 'Resumen' ? 'is-active' : ''}`} onClick={() => navigateTo('Resumen')} type="button">
            <LayoutDashboard size={18} /> Resumen
          </button>
          <button className={`nav-item ${activeNav === 'Proyectos' ? 'is-active' : ''}`} onClick={() => navigateTo('Proyectos')} type="button">
            <FolderKanban size={18} /> Proyectos <span className="nav-count">{projects.length}</span>
          </button>
          <button className={`nav-item ${activeNav === 'Tareas' ? 'is-active' : ''}`} onClick={() => navigateTo('Tareas')} type="button">
            <ClipboardList size={18} /> Tareas <span className="nav-count">{tasks.length}</span>
          </button>
          <div className="nav-divider" />
          <span className="nav-caption">EQUIPO</span>
          <button className="nav-item" type="button" onClick={() => setActiveNav('Equipo')}>
            <Users size={18} /> Colaboradores
          </button>
        </nav>

        <div className="sidebar-bottom">
          <div className="online-indicator"><span /> Entorno de demostración</div>
          <button className="profile-button" type="button">
            <span className="avatar avatar-dark">LM</span>
            <span className="profile-copy"><strong>Lucía Méndez</strong><small>Administradora</small></span>
            <ArrowUpRight size={15} />
          </button>
        </div>
      </aside>

      <main className="main-area">
        <header className="topbar">
          <div className="breadcrumb"><span>Estudio Norte</span><span className="breadcrumb-slash">/</span><strong>{activeNav}</strong></div>
          <div className="topbar-actions">
            <span className="today-label">Martes, 29 de septiembre</span>
            <button className="icon-button notification-button" type="button" aria-label="Notificaciones" title="Notificaciones">
              <Bell size={18} /><span className="notification-dot" />
            </button>
            <span className="avatar avatar-top">LM</span>
          </div>
        </header>

        <div className="page-content">
          <section className="page-heading">
            <div>
              <div className="eyebrow"><span className="eyebrow-line" /> VISTA GENERAL</div>
              <h1>Buen día, Lucía<span className="heading-period">.</span></h1>
              <p>Esto es lo que está pasando con tu equipo hoy.</p>
            </div>
            <button className="primary-button" type="button" onClick={() => setIsTaskFormOpen(true)}>
              <Plus size={17} strokeWidth={2.5} /> Nueva tarea
            </button>
          </section>

          <section className="metrics" aria-label="Resumen de actividad">
            <article className="metric metric-highlight">
              <div className="metric-top"><span>Proyectos activos</span><FolderKanban size={17} /></div>
              <div className="metric-number">{projects.length}<span className="metric-unit"> proyectos</span></div>
              <div className="metric-note"><span className="metric-marker green-marker" /> Todos en marcha</div>
            </article>
            <article className="metric">
              <div className="metric-top"><span>Tareas abiertas</span><ClipboardList size={17} /></div>
              <div className="metric-number">{tasks.filter((task) => task.status !== 'Completada').length}<span className="metric-unit"> tareas</span></div>
              <div className="metric-note"><span className="metric-marker coral-marker" /> {tasks.filter((task) => task.status === 'En progreso').length} en progreso</div>
            </article>
            <article className="metric">
              <div className="metric-top"><span>Completadas</span><CheckCircle2 size={17} /></div>
              <div className="metric-number">{tasks.filter((task) => task.status === 'Completada').length}<span className="metric-unit"> tareas</span></div>
              <div className="metric-note"><span className="metric-marker blue-marker" /> En todos los proyectos</div>
            </article>
          </section>

          <section className="projects-section" id="projects">
            <div className="section-heading">
              <div><div className="section-kicker">ENFOQUE DEL EQUIPO</div><h2>Proyectos</h2></div>
              <button className="text-button" type="button" onClick={() => { setActiveProject(null); setActiveNav('Proyectos') }}>
                Ver todos <ArrowUpRight size={15} />
              </button>
            </div>
            <div className="project-grid">
              {projects.map((project, index) => (
                <button
                  className={`project-card ${activeProject === project.id ? 'project-selected' : ''}`}
                  key={project.id}
                  onClick={() => { setActiveProject(activeProject === project.id ? null : project.id); setActiveNav('Proyectos') }}
                  type="button"
                  aria-pressed={activeProject === project.id}
                >
                  <div className="project-card-top">
                    <span className={`project-symbol ${project.color}`}><FolderKanban size={17} /></span>
                    <span className="project-index">0{index + 1}</span>
                  </div>
                  <h3>{project.name}</h3>
                  <p className="project-objective">{project.objective}</p>
                  <div className="project-progress-label"><span>Avance</span><strong>{project.progress}%</strong></div>
                  <div className="progress-track"><span className={project.color} style={{ width: `${project.progress}%` }} /></div>
                  <div className="project-footer">
                    <span className="avatar-stack" aria-label={`Equipo de ${project.owner}`}>
                      {project.initials.map((initials, memberIndex) => <span className={`mini-avatar mini-${memberIndex}`} key={initials}>{initials}</span>)}
                    </span>
                    <span className="project-due">Vence {project.due}</span>
                  </div>
                </button>
              ))}
            </div>
          </section>

          <section className="tasks-section" id="tasks">
            <div className="section-heading task-heading">
              <div><div className="section-kicker">TRABAJO DEL EQUIPO</div><h2>{activeProject ? getProjectName(activeProject) : 'Tareas recientes'}</h2></div>
              <div className="view-switch" aria-label="Modo de visualización">
                <button className={view === 'list' ? 'view-active' : ''} type="button" onClick={() => setView('list')} aria-label="Vista de lista" title="Vista de lista"><List size={17} /></button>
                <button className={view === 'board' ? 'view-active' : ''} type="button" onClick={() => setView('board')} aria-label="Vista de tablero" title="Vista de tablero"><FolderKanban size={17} /></button>
              </div>
            </div>

            <div className="task-toolbar">
              <div className="status-tabs" role="tablist" aria-label="Filtrar tareas por estado">
                {statusOptions.map((status) => (
                  <button className={activeStatus === status ? 'status-tab active-tab' : 'status-tab'} key={status} onClick={() => setActiveStatus(status)} type="button" role="tab" aria-selected={activeStatus === status}>
                    {status}<span>{status === 'Todas' ? visibleTasks.length : tasks.filter((task) => task.status === status && (activeProject === null || task.projectId === activeProject)).length}</span>
                  </button>
                ))}
              </div>
              <label className="search-field">
                <Search size={16} />
                <input aria-label="Buscar tarea o responsable" onChange={(event) => setSearch(event.target.value)} placeholder="Buscar tarea..." value={search} />
              </label>
            </div>

            {view === 'list' ? (
              <div className="table-wrap">
                <table className="task-table">
                  <thead><tr><th>Tarea</th><th>Proyecto</th><th>Responsable</th><th>Vencimiento</th><th>Prioridad</th><th>Estado</th></tr></thead>
                  <tbody>
                    {visibleTasks.map((task) => (
                      <tr key={task.id}>
                        <td><span className={`task-check ${task.status === 'Completada' ? 'task-done' : ''}`}>{task.status === 'Completada' && <Check size={12} />}</span><span className={task.status === 'Completada' ? 'completed-title' : ''}>{task.title}</span></td>
                        <td><span className="table-project">{getProjectName(task.projectId)}</span></td>
                        <td><span className="assignee"><span className="avatar avatar-small">{task.initials}</span>{task.assignee}</span></td>
                        <td><span className={task.due === 'Hoy' ? 'due-today' : 'due-date'}>{task.due}</span></td>
                        <td><span className={`priority priority-${task.priority.toLowerCase()}`}>{task.priority}</span></td>
                        <td><select className={`status-select status-${task.status.replace(' ', '-').toLowerCase()}`} aria-label={`Estado de ${task.title}`} onChange={(event) => updateTaskStatus(task.id, event.target.value)} value={task.status}>{columns.map((status) => <option key={status}>{status}</option>)}</select></td>
                      </tr>
                    ))}
                    {visibleTasks.length === 0 && <tr><td className="empty-state" colSpan="6">No hay tareas para estos filtros.</td></tr>}
                  </tbody>
                </table>
              </div>
            ) : (
              <div className="board-grid">
                {columns.map((status) => {
                  const columnTasks = visibleTasks.filter((task) => task.status === status)
                  return <div className="board-column" key={status}>
                    <div className="board-column-heading"><span className={`column-dot column-${status.replace(' ', '-').toLowerCase()}`} />{status}<span className="column-count">{columnTasks.length}</span></div>
                    {columnTasks.map((task) => <article className="board-task" key={task.id}>
                      <span className={`priority priority-${task.priority.toLowerCase()}`}>{task.priority}</span>
                      <h3>{task.title}</h3>
                      <p>{getProjectName(task.projectId)}</p>
                      <div className="board-task-footer"><span className="assignee"><span className="avatar avatar-small">{task.initials}</span>{task.assignee}</span><span>{task.due}</span></div>
                      <select className="board-status-select" aria-label={`Mover ${task.title}`} onChange={(event) => updateTaskStatus(task.id, event.target.value)} value={task.status}>{columns.map((option) => <option key={option}>{option}</option>)}</select>
                    </article>)}
                  </div>
                })}
              </div>
            )}
            <div className="table-footnote"><span className="live-dot" /> Mostrando {visibleTasks.length} de {tasks.length} tareas <span className="footnote-separator">·</span> Datos de demostración</div>
          </section>
          <footer className="page-footer"><span>ESTUDIO NORTE <span className="footer-period">/</span> GESTIÓN DE PROYECTOS</span><span>Prototipo de interfaz · 2026</span></footer>
        </div>
      </main>

      {isTaskFormOpen && <div className="modal-backdrop" onMouseDown={(event) => { if (event.target === event.currentTarget) setIsTaskFormOpen(false) }}>
        <section className="task-modal" role="dialog" aria-modal="true" aria-labelledby="modal-title">
          <div className="modal-heading"><div><span className="section-kicker">NUEVO ELEMENTO</span><h2 id="modal-title">Crear tarea</h2></div><button className="icon-button" type="button" onClick={() => setIsTaskFormOpen(false)} aria-label="Cerrar"><X size={19} /></button></div>
          <form onSubmit={createTask}>
            <label className="form-field">Título<input name="title" placeholder="¿Qué hay que hacer?" required autoFocus /></label>
            <label className="form-field">Proyecto<select name="projectId" defaultValue={activeProject ?? projects[0].id}>{projects.map((project) => <option key={project.id} value={project.id}>{project.name}</option>)}</select></label>
            <div className="form-row">
              <label className="form-field">Responsable<input name="assignee" placeholder="Nombre y apellido" required /></label>
              <label className="form-field">Prioridad<select name="priority" defaultValue="Media"><option>Baja</option><option>Media</option><option>Alta</option></select></label>
            </div>
            <label className="form-field">Vencimiento<input name="due" placeholder="Ej. 10 oct" /></label>
            <div className="modal-actions"><button className="secondary-button" type="button" onClick={() => setIsTaskFormOpen(false)}>Cancelar</button><button className="primary-button" type="submit"><Plus size={16} /> Crear tarea</button></div>
          </form>
        </section>
      </div>}
    </div>
  )
}

export default App
