import React, { useState } from "react";
import { 
  ChevronLeft, 
  ChevronRight, 
  Calendar as CalendarIcon, 
  Clock, 
  User, 
  FileText, 
  Upload, 
  Trash2, 
  Paperclip,
  CheckCircle,
  Plus
} from "lucide-react";

// ============================================
// TYPES & INTERFACES
// ============================================

export interface Appointment {
  id: string;
  title: string;
  date: Date; // Specific schedule date
  type: "Control Prenatal" | "Pediatría" | "Psicología" | "Laboratorio" | "Otro";
  provider: string;
  notes?: string;
  status: "PROGRAMADA" | "COMPLETADA" | "CANCELADA";
  attachedDocumentIds: string[];
}

export interface MedicalDocument {
  id: string;
  name: string;
  uploadedAt: Date;
  appointmentId?: string;
  type: "Receta" | "Orden médica" | "Incapacidad" | "Ecografía" | "Laboratorio" | "Otro";
  documentText?: string;
}

// Helper for joining Tailwind classes (Standard shadcn utility)
function cn(...classes: (string | undefined | null | boolean)[]) {
  return classes.filter(Boolean).join(" ");
}

// ============================================
// SHADCN/UI STYLE CALENDAR COMPONENT
// ============================================
interface CalendarProps {
  selectedDate: Date | null;
  onSelectDate: (date: Date | null) => void;
  appointments: Appointment[];
  className?: string;
}

export const Calendar: React.FC<CalendarProps> = ({
  selectedDate,
  onSelectDate,
  appointments,
  className
}) => {
  const [currentDate, setCurrentDate] = useState<Date>(new Date());
  
  const year = currentDate.getFullYear();
  const month = currentDate.getMonth();

  const meses = [
    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
  ];

  const diasSemana = ["Lu", "Ma", "Mi", "Ju", "Vi", "Sa", "Do"];

  // Calculate start of month offset
  const firstDayOfMonth = new Date(year, month, 1);
  const startDayOfWeek = (firstDayOfMonth.getDay() + 6) % 7; // Align Monday to index 0
  const totalDaysInMonth = new Date(year, month + 1, 0).getDate();

  const handlePrevMonth = () => {
    setCurrentDate(new Date(year, month - 1, 1));
  };

  const handleNextMonth = () => {
    setCurrentDate(new Date(year, month + 1, 1));
  };

  // Check if a day has any scheduled appointments
  const hasAppointmentOnDay = (dayNum: number) => {
    return appointments.some(app => {
      const appDate = new Date(app.date);
      return (
        appDate.getDate() === dayNum &&
        appDate.getMonth() === month &&
        appDate.getFullYear() === year
      );
    });
  };

  const isToday = (dayNum: number) => {
    const today = new Date();
    return (
      today.getDate() === dayNum &&
      today.getMonth() === month &&
      today.getFullYear() === year
    );
  };

  const isSelected = (dayNum: number) => {
    if (!selectedDate) return false;
    return (
      selectedDate.getDate() === dayNum &&
      selectedDate.getMonth() === month &&
      selectedDate.getFullYear() === year
    );
  };

  const handleDayClick = (dayNum: number) => {
    const dateClicked = new Date(year, month, dayNum);
    if (selectedDate && isSelected(dayNum)) {
      onSelectDate(null); // Click selected again to clear filter
    } else {
      onSelectDate(dateClicked);
    }
  };

  // Calendar container designed with calm maternal shadcn palette
  // (creme background, lila accents, deep purple primary)
  return (
    <div className={cn("p-4 bg-white/90 border border-purple-100 rounded-2xl shadow-sm max-w-sm w-full mx-auto backdrop-blur-sm", className)}>
      {/* Header Month Navigation */}
      <div className="flex items-center justify-between mb-4">
        <h2 className="text-sm font-semibold text-purple-900 font-serif">
          {meses[month]} {year}
        </h2>
        <div className="flex space-x-1">
          <button 
            type="button"
            onClick={handlePrevMonth}
            className="p-1 px-2 text-purple-700 transition hover:bg-purple-50 rounded-lg border border-purple-100 hover:text-purple-900"
          >
            <ChevronLeft className="w-4 h-4" />
          </button>
          <button 
            type="button"
            onClick={handleNextMonth}
            className="p-1 px-2 text-purple-700 transition hover:bg-purple-50 rounded-lg border border-purple-100 hover:text-purple-900"
          >
            <ChevronRight className="w-4 h-4" />
          </button>
        </div>
      </div>

      {/* Weekdays Grid */}
      <div className="grid grid-cols-7 gap-1 text-center mb-1 text-xs font-medium text-pink-500">
        {diasSemana.map(dia => (
          <div key={dia} className="py-1">
            {dia}
          </div>
        ))}
      </div>

      {/* Days Grid */}
      <div className="grid grid-cols-7 gap-1 text-center text-sm font-medium">
        {/* Fill leading empty spaces */}
        {Array.from({ length: startDayOfWeek }).map((_, idx) => (
          <div key={`empty-${idx}`} className="p-2 text-gray-300 pointer-events-none select-none">
            -
          </div>
        ))}

        {/* Render visible month days */}
        {Array.from({ length: totalDaysInMonth }).map((_, idx) => {
          const dayNum = idx + 1;
          const daySelected = isSelected(dayNum);
          const dayToday = isToday(dayNum);
          const hasEvent = hasAppointmentOnDay(dayNum);

          return (
            <button
              key={`day-${dayNum}`}
              type="button"
              onClick={() => handleDayClick(dayNum)}
              className={cn(
                "relative p-2 rounded-xl transition hover:bg-purple-50 flex flex-col items-center justify-center h-9 w-9 mx-auto focus:outline-none focus:ring-2 focus:ring-purple-200",
                dayToday && "border border-pink-400 text-pink-600",
                daySelected && "bg-purple-700 text-white hover:bg-purple-800 border-none shadow-sm"
              )}
            >
              <span className="z-10 text-xs">{dayNum}</span>
              {/* Event indicator badge dot */}
              {hasEvent && (
                <span 
                  className={cn(
                    "absolute bottom-0.5 w-1 h-1 rounded-full z-20",
                    daySelected ? "bg-white" : "bg-pink-500"
                  )}
                />
              )}
            </button>
          );
        })}
      </div>
    </div>
  );
};


// ============================================
// SHADCN/UI STYLE APPOINTMENT CARD COMPONENT
// ============================================
interface AppointmentCardProps {
  appointment: Appointment;
  linkedDocuments: MedicalDocument[];
  onUploadDocument: (appointmentId: string) => void;
  onRemoveDocumentLink?: (docId: string) => void;
  className?: string;
}

export const AppointmentCard: React.FC<AppointmentCardProps> = ({
  appointment,
  linkedDocuments,
  onUploadDocument,
  onRemoveDocumentLink,
  className
}) => {
  // Styles depending on categories
  const categoryStyles = {
    "Control Prenatal": "bg-purple-50 text-purple-700 border-purple-100",
    "Pediatría": "bg-teal-50 text-teal-700 border-teal-100",
    "Psicología": "bg-rose-50 text-rose-700 border-rose-100",
    "Laboratorio": "bg-blue-50 text-blue-700 border-blue-100",
    "Otro": "bg-gray-50 text-gray-700 border-gray-100"
  };

  const formattedDate = appointment.date.toLocaleDateString("es-ES", {
    weekday: "long",
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit"
  });

  return (
    <div className={cn("p-5 bg-white border border-slate-100 rounded-2xl shadow-sm flex flex-col space-y-4 hover:shadow-md transition duration-300", className)}>
      
      {/* Header section */}
      <div className="flex justify-between items-start">
        <div className="space-y-1">
          <span className={cn("inline-flex items-center px-2.5 py-0.5 rounded-full text-2xs font-bold border", categoryStyles[appointment.type])}>
            {appointment.type}
          </span>
          <h3 className="text-base font-bold text-slate-800 leading-snug">
            {appointment.title}
          </h3>
        </div>
        <span className={cn(
          "px-2 py-0.5 text-2xs font-semibold rounded-md uppercase",
          appointment.status === "PROGRAMADA" && "bg-amber-50 text-amber-600 border border-amber-100",
          appointment.status === "COMPLETADA" && "bg-green-50 text-green-600 border border-green-100",
          appointment.status === "CANCELADA" && "bg-rose-50 text-rose-500 border border-rose-100"
        )}>
          {appointment.status.toLowerCase()}
        </span>
      </div>

      {/* Detail row */}
      <div className="space-y-2 text-xs text-slate-500">
        <div className="flex items-center gap-2">
          <CalendarIcon className="w-3.5 h-3.5 text-purple-400 shrink-0" />
          <span className="capitalize">{formattedDate}</span>
        </div>
        <div className="flex items-center gap-2">
          <User className="w-3.5 h-3.5 text-teal-500 shrink-0" />
          <span>Profesional: <strong className="text-slate-700">{appointment.provider}</strong></span>
        </div>
        {appointment.notes && (
          <div className="mt-1 p-2 bg-slate-50 border-l-2 border-slate-300 rounded text-slate-600 leading-relaxed italic">
            "{appointment.notes}"
          </div>
        )}
      </div>

      {/* Linked Documents list inside card footer */}
      <div className="pt-3 border-t border-slate-150">
        <div className="flex items-center justify-between mb-2">
          <h4 className="text-xs font-bold text-slate-700 flex items-center gap-1.5">
            <Paperclip className="w-3.5 h-3.5 text-purple-500" />
            Documentos Médicos Asociados ({linkedDocuments.length})
          </h4>
        </div>

        {linkedDocuments.length === 0 ? (
          <p className="text-2xs text-slate-400 italic mb-3">
            No se han escaneado ni enlazado recetas, ecografías o informes para esta cita.
          </p>
        ) : (
          <div className="space-y-1.5 mb-3 max-h-32 overflow-y-auto pr-1">
            {linkedDocuments.map(doc => (
              <div 
                key={doc.id} 
                className="flex items-center justify-between p-2 rounded-xl bg-purple-50/50 hover:bg-purple-50 border border-purple-100/40 transition text-2xs"
              >
                <div className="flex items-center gap-2 min-w-0">
                  <FileText className="w-3.5 h-3.5 text-teal-600" />
                  <div className="truncate">
                    <p className="font-bold text-slate-700 truncate">{doc.name}</p>
                    <p className="text-3xs text-slate-400 capitalize">{doc.type}</p>
                  </div>
                </div>
                <div className="flex items-center gap-1">
                  {doc.documentText && (
                    <span 
                      title={doc.documentText}
                      className="px-1.5 py-0.5 rounded bg-teal-100 text-teal-800 text-3xs font-semibold select-none cursor-help"
                    >
                      OCR OK
                    </span>
                  )}
                  {onRemoveDocumentLink && (
                    <button
                      type="button"
                      onClick={() => onRemoveDocumentLink(doc.id)}
                      className="text-rose-500 hover:text-rose-700 p-1 rounded-md transition"
                      aria-label="Desasociar documento"
                    >
                      <Trash2 className="w-3.5 h-3.5" />
                    </button>
                  )}
                </div>
              </div>
            ))}
          </div>
        )}

        {/* Action Button: Upload and Link Document */}
        <button
          type="button"
          onClick={() => onUploadDocument(appointment.id)}
          className="w-full py-2 bg-gradient-to-r from-purple-700 to-pink-600 hover:from-purple-800 hover:to-pink-700 text-white rounded-xl text-xs font-bold transition duration-300 flex items-center justify-center gap-2 group shadow-sm hover:shadow"
        >
          <Upload className="w-3.5 h-3.5 group-hover:-translate-y-0.5 transition-transform" />
          Escanear & Vincular Documento OCR
        </button>
      </div>
    </div>
  );
};


// ============================================
// CONSOLIDATED DEMO WITH STATE MANAGEMENT
// ============================================

export const OnboardingCalendarDemo: React.FC = () => {
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);
  
  // Fake list of user appointments
  const [appointments, setAppointments] = useState<Appointment[]>([
    {
      id: "app-1",
      title: "Control Obstétrico Trimestre II",
      date: new Date(new Date().setDate(new Date().getDate() + 1)), // Tomorrow
      type: "Control Prenatal",
      provider: "Dra. Sofía Martínez",
      notes: "Ir con vejiga llena para la ecografía genética.",
      status: "PROGRAMADA",
      attachedDocumentIds: ["doc-1"]
    },
    {
      id: "app-2",
      title: "Psicoterapia Maternidad",
      date: new Date(new Date().setDate(new Date().getDate() + 3)), // In 3 days
      type: "Psicología",
      provider: "Lic. Claudio Rivas",
      notes: "Sesión de apoyo sobre ansiedad perinatal.",
      status: "PROGRAMADA",
      attachedDocumentIds: []
    }
  ]);

  // Fake list of uploaded medical documents
  const [documents, setDocuments] = useState<MedicalDocument[]>([
    {
      id: "doc-1",
      name: "Orden de Ecografía Previa",
      uploadedAt: new Date(),
      appointmentId: "app-1",
      type: "Ecografía",
      documentText: "Paciente: Ana Gómez. Médico: Dra. Sofía Martínez. Solicito ecografia genetica."
    },
    {
      id: "doc-2",
      name: "Prescripción de Ácido Fólico",
      uploadedAt: new Date(Date.now() - 24 * 60 * 60 * 1000),
      type: "Receta",
      documentText: "Ácido Fólico 5mg. Tomar 1 tableta diaria por las mañanas."
    }
  ]);

  // Handle linking a new simulated document scanning to this appointment
  const handleUploadAndLink = (appointmentId: string) => {
    const docName = prompt("Introduce el nombre del documento a escanear (Ej: Receta de Vitaminas):");
    if (!docName) return;

    const newDocId = `doc-${Date.now()}`;
    const newDoc: MedicalDocument = {
      id: newDocId,
      name: docName,
      uploadedAt: new Date(),
      appointmentId: appointmentId,
      type: "Receta",
      documentText: `Contenido escaneado de: ${docName}. Sincronizado dinámicamente con Gemini AI.`
    };

    setDocuments(prev => [...prev, newDoc]);
    setAppointments(prev => prev.map(app => {
      if (app.id === appointmentId) {
        return {
          ...app,
          attachedDocumentIds: [...app.attachedDocumentIds, newDocId]
        };
      }
      return app;
    }));
  };

  // Handle unlinking the document
  const handleUnlinkDocument = (docId: string) => {
    setDocuments(prev => prev.map(doc => {
      if (doc.id === docId) {
        return { ...doc, appointmentId: undefined };
      }
      return doc;
    }));
  };

  // Filter list of appointments based on the calendar selected date
  const filteredAppointments = selectedDate
    ? appointments.filter(app => {
        const appDate = new Date(app.date);
        return (
          appDate.getDate() === selectedDate.getDate() &&
          appDate.getMonth() === selectedDate.getMonth() &&
          appDate.getFullYear() === selectedDate.getFullYear()
        );
      })
    : appointments;

  return (
    <div className="min-h-screen bg-slate-50 py-10 px-4 flex flex-col items-center">
      <div className="max-w-4xl w-full grid grid-cols-1 md:grid-cols-12 gap-8">
        
        {/* Left Column: Calendar filter widget */}
        <div className="md:col-span-5 space-y-4">
          <div>
            <h1 className="text-xl font-bold font-serif text-slate-800">Calendario de Maternidad</h1>
            <p className="text-xs text-slate-500">Filtra y organiza tus citas médicas y recetas por fecha</p>
          </div>
          
          <Calendar 
            selectedDate={selectedDate}
            onSelectDate={setSelectedDate}
            appointments={appointments}
          />
          
          {selectedDate && (
            <div className="p-3 bg-purple-50 text-purple-900 rounded-xl text-2xs flex items-center justify-between">
              <span>Filtrado: {selectedDate.toLocaleDateString("es-ES")}</span>
              <button 
                onClick={() => setSelectedDate(null)}
                className="font-bold underline hover:text-purple-700"
              >
                Limpiar Filtro
              </button>
            </div>
          )}
        </div>

        {/* Right Column: Upcoming Control Schedules & Linked Documents */}
        <div className="md:col-span-7 space-y-4">
          <div className="flex justify-between items-center">
            <h2 className="text-sm font-bold uppercase tracking-wider text-slate-400">
              {selectedDate ? "Citas para este día" : "Próximos Controles y Citas"} ({filteredAppointments.length})
            </h2>
          </div>

          {filteredAppointments.length === 0 ? (
            <div className="p-8 text-center bg-white border border-dashed border-slate-200 rounded-2xl">
              <CalendarIcon className="w-8 h-8 text-slate-300 mx-auto mb-2" />
              <p className="text-xs text-slate-550 font-medium">No se registran citas médicas para esta fecha.</p>
              <p className="text-3xs text-slate-400 mt-1">Intenta seleccionar otro día con un punto de evento o crea una cita.</p>
            </div>
          ) : (
            <div className="space-y-4">
              {filteredAppointments.map(app => {
                const linkedDocs = documents.filter(doc => doc.appointmentId === app.id);
                return (
                  <AppointmentCard
                    key={app.id}
                    appointment={app}
                    linkedDocuments={linkedDocs}
                    onUploadDocument={handleUploadAndLink}
                    onRemoveDocumentLink={handleUnlinkDocument}
                  />
                );
              })}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
