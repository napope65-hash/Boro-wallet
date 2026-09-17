import React from 'react';
import { NotificationItem } from '../../types';

interface NotificationsDrawerProps {
  isOpen: boolean;
  onClose: () => void;
  notifications: NotificationItem[];
  onMarkAllAsRead: () => void;
  onClearNotifications: () => void;
}

export const NotificationsDrawer: React.FC<NotificationsDrawerProps> = ({
  isOpen,
  onClose,
  notifications,
  onMarkAllAsRead,
  onClearNotifications,
}) => {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-end sm:items-center justify-center p-0 sm:p-4 bg-black/50 backdrop-blur-sm animate-in fade-in">
      <div className="w-full sm:max-w-md bg-white rounded-t-3xl sm:rounded-3xl shadow-2xl flex flex-col max-h-[90vh] overflow-hidden">
        {/* Header */}
        <div className="flex items-center justify-between px-6 pt-5 pb-3 border-b border-gray-100">
          <div className="flex items-center gap-2">
            <span className="material-symbols-outlined text-[#1A1C1F] text-[22px]">
              notifications
            </span>
            <h3 className="text-[18px] font-bold text-[#1A1C1F]">Notifications</h3>
          </div>

          <div className="flex items-center gap-2">
            <button
              onClick={onMarkAllAsRead}
              className="text-[12px] font-semibold text-[#e70054] hover:underline"
            >
              Mark all read
            </button>
            <button
              onClick={onClose}
              className="w-8 h-8 rounded-full bg-[#F4F5F6] hover:bg-[#EDEDF2] flex items-center justify-center text-[#404754] transition-colors"
            >
              <span className="material-symbols-outlined text-[18px]">close</span>
            </button>
          </div>
        </div>

        {/* List */}
        <div className="p-6 overflow-y-auto space-y-3">
          {notifications.length === 0 ? (
            <div className="py-12 text-center text-[#404754]">
              <span className="material-symbols-outlined text-[36px] text-gray-300 mb-2">
                notifications_off
              </span>
              <p className="text-[14px]">You're all caught up!</p>
            </div>
          ) : (
            notifications.map((notif) => (
              <div
                key={notif.id}
                className={`p-3.5 rounded-2xl border transition-all flex items-start gap-3 ${
                  notif.read
                    ? 'bg-[#F4F5F6]/60 border-transparent opacity-75'
                    : 'bg-[#F4F5F6] border-[#e70054]/20 shadow-xs'
                }`}
              >
                <div
                  className={`w-9 h-9 rounded-full flex items-center justify-center flex-shrink-0 ${
                    notif.type === 'price'
                      ? 'bg-[rgba(255,0,94,0.12)] text-[#e70054]'
                      : notif.type === 'reward'
                      ? 'bg-green-100 text-green-700'
                      : 'bg-blue-100 text-blue-700'
                  }`}
                >
                  <span className="material-symbols-outlined text-[18px]">
                    {notif.type === 'price'
                      ? 'trending_up'
                      : notif.type === 'reward'
                      ? 'payments'
                      : 'shield'}
                  </span>
                </div>

                <div className="flex-1 min-w-0">
                  <div className="flex items-center justify-between gap-1">
                    <h4 className="text-[14px] font-semibold text-[#1A1C1F] truncate">
                      {notif.title}
                    </h4>
                    <span className="text-[11px] text-[#404754] flex-shrink-0">
                      {notif.timeAgo}
                    </span>
                  </div>
                  <p className="text-[13px] text-[#404754] mt-0.5 leading-snug">
                    {notif.message}
                  </p>
                </div>
              </div>
            ))
          )}

          {notifications.length > 0 && (
            <button
              onClick={onClearNotifications}
              className="w-full py-2.5 rounded-xl border border-gray-200 text-[13px] font-semibold text-[#404754] hover:bg-[#F4F5F6] transition-colors mt-2"
            >
              Clear All Notifications
            </button>
          )}
        </div>
      </div>
    </div>
  );
};
